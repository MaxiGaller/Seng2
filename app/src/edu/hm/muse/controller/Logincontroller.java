package edu.hm.muse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.security.SecureRandom;
import java.sql.Types;

import static org.springframework.web.util.WebUtils.getCookie;

@Controller
public class Logincontroller {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    CookieHelper cookieHelper;
    @Autowired
    SaltErstellen saltErstellen;
    @Autowired
    private ActiveUsers users;
    @Autowired
    private Token token;


    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @RequestMapping(value = "/login.secu", method = RequestMethod.GET)
    public ModelAndView showLoginScreen(HttpServletResponse response, HttpSession session,
                                        @RequestParam(value = "login", required = false) String loginStatus) {
        ModelAndView mv = new ModelAndView("login");
        if (loginStatus != null && loginStatus.equals("failed")) {
            mv.addObject("msg", "Fehler beim Login. Bitte Daten eingeben");
        }
        else {
            mv.addObject("msg", "Zum Einloggen bitte Benutzernamen und Passwort eintragen.");
        }
        Integer token = getNewToken();
        mv.addObject("csrfToken", token);
        Cookie loginCookie = new Cookie("login", String.valueOf(token));
        response.addCookie(loginCookie);
        session.setAttribute("csrfToken", token);
        return mv;
    }




    @RequestMapping(value = "/login.secu", method = RequestMethod.POST)
    public ModelAndView doSomeLogin(@RequestParam(value = "mname", required = false) String mname,
                                    @RequestParam(value = "mpwd", required = false) String mpwd,
                                    @RequestParam(value = "Id", required = false) String id,
                                    HttpServletResponse response, HttpSession session,
                                    HttpServletRequest request) {
        if (null == mname || null == mpwd || mname.isEmpty() || mpwd.isEmpty()) {
            return new ModelAndView("redirect:login.secu?login=failed");
        }

        if (!isUserInputValid(mname)) {
            ModelAndView mv = new ModelAndView("login");
            mv.addObject("msg", "Nur Buchstaben und Zahlen sind erlaubt!!");
            return mv;
        }
        if (!isLoginNameTaken(mname)) {
            return new ModelAndView("redirect:login.secu");
        }

        //TODO: Try Catch drum rum!!

        String getSalt = "select salt from M_USER where muname = ?";
        String salt = jdbcTemplate.queryForObject(getSalt, new Object[]{mname}, String.class);

        String saltedPw = salt + mpwd;

        String getId = "select id from M_USER where muname = ?";
        String ID1 = jdbcTemplate.queryForObject(getId, new Object[]{mname}, String.class);

        String getName = "select muname from M_USER where muname = ?";
        String name = jdbcTemplate.queryForObject(getName, new Object[]{mname}, String.class);


        String hpwd = HashenController.hashen256(saltedPw);

        //This is the sql statement
        String sql = "select count(*) from M_USER where muname = ? and mpwd = ?";

        int res = 0;
        try {
            res = jdbcTemplate.queryForInt(sql,new Object[] {mname, hpwd}, new int[]{Types.VARCHAR, Types.VARCHAR});

            if (session.getAttribute("csrfToken") == null || getCookie(request, "login") == null) {
                return new ModelAndView("redirect:login.secu");
            }

            int csrfTokenFromSession = (int) session.getAttribute("csrfToken");
            int csrfTokenFromCookie = Integer.parseInt(getCookie(request, "login").getValue());
            if (csrfTokenFromSession != 0 && csrfTokenFromCookie != 0 && res > 0) {
                if (csrfTokenFromCookie == csrfTokenFromSession) {
                    session.setAttribute("user", mname);
                    session.setAttribute("Id", ID1);
                    Cookie loginCookie = new Cookie("loggedIn", String.valueOf(token));
                    cookieHelper.eraseCookie(request, response, "login");
                    response.addCookie(loginCookie);
                    session.setAttribute("login", true);
                    session.removeAttribute("csrftoken");
                    String sessionId = session.getId();
                    //TODO
                    int newToken= token.getNewToken();
                    session.setAttribute("usertoken", newToken);
                    users.setUser(mname, newToken);

                    return new ModelAndView("redirect:projects.secu?justLoggedIn=1");
                }
            }

        } catch (DataAccessException e) {
            return new ModelAndView("redirect:login.secu?login=failed");
        }

        //Ohhhhh not correct try again
        return returnToLogin(session);
    }

    private ModelAndView returnToLogin(HttpSession session) {
        //Ohhhhh not correct try again
        ModelAndView mv = new ModelAndView("redirect:login.secu");
        mv.addObject("msg", "Sorry try again");
        session.invalidate();
        return mv;
    }

    private boolean isUserInputValid(String mname) {
        return mname.matches("[A-Za-z0-9]+");
    }


    private boolean isLoginNameTaken(String mname) {
        String sql = "select count(*) from M_USER where muname = ?";
        int res=0;
        try {
            res = jdbcTemplate.queryForInt(sql, new Object[] {mname}, new int[] {Types.VARCHAR});
            if (res > 0) {
                return true;
            }
        } catch (DataAccessException e) {
            ModelAndView mv = new ModelAndView("redirect:login.secu?login=failed");
        }
        return false;
    }

    private int getNewToken() {
        SecureRandom random = new SecureRandom();
        return random.nextInt();
    }
}