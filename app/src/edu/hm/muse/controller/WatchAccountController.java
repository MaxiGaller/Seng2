package edu.hm.muse.controller;

import edu.hm.muse.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.security.MessageDigest;
import java.sql.Types;


@Controller
public class WatchAccountController {
// extends functions

    @Autowired
    private LoginHelper loginHelper;

    private JdbcTemplate jdbcTemplate;

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @RequestMapping(value = "/internchange.secu", method = RequestMethod.GET)
    public ModelAndView showAccountToChange(HttpSession session, HttpServletRequest request) {
        if ((null == session) || (null == session.getAttribute("login")) || (!((Boolean) session.getAttribute("login")))) {
            return new ModelAndView("redirect:login.secu");
        }

        if (loginHelper.isNotLoggedIn(request, session)) {
            return new ModelAndView("redirect:login.secu");
        }

        String uname = (String) session.getAttribute("user");

        String sql = "select ID, muname,mpwd from M_USER where muname = ?";

        User u = jdbcTemplate.queryForObject(sql, new Object[]{uname}, new int[]{Types.VARCHAR}, new UserMapper());

        ModelAndView mv = new ModelAndView("internchange");
        mv.addObject("userDomain", u);

        return mv;
    }



    @RequestMapping(value = "/intern.secu", method = RequestMethod.GET)
    public ModelAndView showAccount(HttpSession session, HttpServletRequest request) {
        if ((null == session) || (null == session.getAttribute("login")) || (!((Boolean) session.getAttribute("login")))) {
            return new ModelAndView("redirect:login.secu");
        }

        if (loginHelper.isNotLoggedIn(request, session)) {
            return new ModelAndView("redirect:login.secu");
        }

        String uname = (String) session.getAttribute("user");

        String sql = "select ID, muname,mpwd from M_USER where muname = ?";

        User u = jdbcTemplate.queryForObject(sql, new Object[]{uname}, new int[]{Types.VARCHAR}, new UserMapper());

        ModelAndView mv = new ModelAndView("intern");
        mv.addObject("userDomain", u);
        String pwd;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
//            digest.reset();
            digest.update(u.getUpwd().getBytes());
            byte[] md = digest.digest();
            StringBuilder hex = new StringBuilder();
            for (byte aMd : md) {
                if (aMd <= 15 && aMd >= 0) {
                    hex.append("0");
                }
                hex.append(Integer.toHexString(0xFF & aMd));
            }
            pwd = hex.toString();
        } catch (Exception e) {
            pwd = "7c6a180b36896a0a8c02787eeafb0e4c";
        }

        mv.addObject("md5pwd", pwd);

        return mv;
    }


    @RequestMapping(value = "/change.secu", method = RequestMethod.POST)
    public ModelAndView changeAccount(HttpSession session,
                                      //@RequestParam(value = "uid", required = true) String uid,
                                      @RequestParam(value = "uname", required = true) String uname,
                                      @RequestParam(value = "upwd", required = true) String upwd,
                                      @RequestParam(value = "upwd1", required = true) String upwd1,
                                      HttpServletRequest request) {

        if ((null == session) || (null == session.getAttribute("login")) || (!((Boolean) session.getAttribute("login")))) {
            return new ModelAndView("redirect:login.secu");
        }

        if (loginHelper.isNotLoggedIn(request, session)) {
            return new ModelAndView("redirect:login.secu");
        }

         if (!isValidPw(upwd)) {
            ModelAndView mv = new ModelAndView("redirect:projects.secu");
            mv.addObject("msg", "Passwort muss Groß- Kleinbuchstaben und Sonderzeichen haben!");
            return mv;
        }

        if (!upwd.equals(upwd1)) {
            ModelAndView mv = new ModelAndView("register");
            mv.addObject("msg", "passen nicht!");
            return mv;
        }

        String uname1 = (String) session.getAttribute("user");

        String getId = "select id from M_USER where muname = ?";
        int ID1 = jdbcTemplate.queryForInt(getId, uname1);

        String getSalt = "select salt from M_USER where muname = ?";
        String salt = jdbcTemplate.queryForObject(getSalt, new Object[]{uname1}, String.class);

        //String id = session.getId();

        String saltedPw = salt + upwd;

        String hpwd = HashenController.hashen256(saltedPw);

        String sql = "update M_USER set  mpwd = ? where ID = ?";

        jdbcTemplate.update(sql, new Object[]{hpwd, ID1}, new int[]{Types.VARCHAR, Types.NUMERIC});
        session.setAttribute("user", uname1);
        return new ModelAndView("redirect:intern.secu");
    }


    private boolean isValidPw(String upwd) {
        String ePattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^+=?!])(?=\\S+$).{6,}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(upwd);
        return m.matches();
    }
}