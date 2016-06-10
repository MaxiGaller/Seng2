package edu.hm.muse.controller;

import edu.hm.muse.exception.SuperFatalAndReallyAnnoyingException;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.security.SecureRandom;
import java.sql.Types;

@Controller
public class RegisterController {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    SaltErstellen saltErstellen;

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @RequestMapping(value = "/register.secu", method = RequestMethod.GET)
    public ModelAndView showLoginScreen() {
        ModelAndView mv = new ModelAndView("register");
        //Show the Msg
        mv.addObject("msg", "Neuen Benutzer anlegen. Bitte Usernamen und Passwort eintragen.");
        return mv;
    }

    @RequestMapping(value = "/register.secu", method = RequestMethod.POST)
    public ModelAndView doSomeRegister(@RequestParam(value = "new_uname", required = false) String new_uname, @RequestParam(value = "new_mpwd", required = false) String new_mpwd, HttpServletResponse response, HttpSession session) {
        if (!(new_uname.matches("[A-Za-z0-9]+"))) {
            ModelAndView mv = new ModelAndView("register");
            mv.addObject("msg", "Nur Buchstaben und Zahlen (ohne Umlaute und Sonderzeichen) sind erlaubt!");
            return mv;
        }


        if (null == new_mpwd || new_uname.isEmpty() || new_mpwd.isEmpty()) {
            throw new SuperFatalAndReallyAnnoyingException("I can not process, because the requestparam new_uname or new_mpwd is empty or null or something like this");
        }


        if (isLoginNameTaken(new_uname)) {
            ModelAndView mv = new ModelAndView("register");
            mv.addObject("msg", "Was los? Den Namen gibts schon. Nimm nen anderen!");
            return mv;
        }

        StringBuilder saltedPw = new StringBuilder();
        byte[] salt = saltErstellen.getNextSalt();
        String saltDB = saltErstellen.byteArrayToString(salt);
        saltedPw.append(saltErstellen.byteArrayToString(salt));
        saltedPw.append(new_mpwd);


        String hpwd = HashenController.hashen256(saltedPw.toString());

        String sqlInsert = "insert into M_USER (muname,mpwd,salt) values (?,?,?)";
        int res = 0;
        try {
            res = jdbcTemplate.update(sqlInsert, new Object[] {new_uname, hpwd, saltDB}, new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
        } catch (DataAccessException e) {
            ModelAndView mv = returnToRegister(session);
        }

        if (res > 0) {
            ModelAndView mv = new ModelAndView("redirect:login.secu?justRegistered=1");
            mv.addObject("msg", "You've been successfully registered, please login:");
            Integer token = getNewToken();
            mv.addObject("csrfToken", token);
            Cookie loginCookie = new Cookie("login", String.valueOf(token));
            response.addCookie(loginCookie);
            session.setAttribute("csrfToken", token);
            return mv;

        }
        return returnToRegister(session);
    }

    private ModelAndView returnToRegister(HttpSession session) {
        //Ohhhhh not correct try again
        return new ModelAndView("redirect:register.secu");
    }


    private boolean isUserInputValid(String mname) {
        return mname.matches("[A-Za-z0-9]+");
    }

    private boolean isLoginNameTaken(String mname) {
        String sql = String.format("select count(*) from M_USER where muname = '%s'", mname);
        int res = 0;
        try {
            res = jdbcTemplate.queryForInt(sql);
            if (res > 0) {
                return true;
            }
        } catch (DataAccessException e) {
            //returnToRegister(session);
            throw new SuperFatalAndReallyAnnoyingException(String.format("Sorry but %sis a bad grammar or has following problem %s", sql, e.getMessage()));
            //return new ModelAndView("redirect:register.secu");

        }
        return false;
    }


    private int getNewToken() { //FIXME: Duplicate Code, see LoginController getNewToken()
        SecureRandom random = new SecureRandom();
        return random.nextInt();
    }

}