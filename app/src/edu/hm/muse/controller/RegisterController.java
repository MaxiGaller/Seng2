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
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

        //Select the Last ID from the Table
    	/*String sqlSelect = "SELECT id FROM M_USER ORDER BY id DESC LIMIT 1";
        int lastId = jdbcTemplate.queryForInt(sqlSelect);
        //Increment the last ID
        lastId++;*/

        //Build the query with the new User and Passwd



        StringBuilder saltedPw = new StringBuilder();
        byte[] salt = saltErstellen.getNextSalt();
        String saltDB = saltErstellen.byteArrayToString(salt);
        saltedPw.append(saltErstellen.byteArrayToString(salt));
        saltedPw.append(new_mpwd);



        //StringBuilder saltedPw = new StringBuilder(); //For building the salt + password String
        //saltErstellen = saltErstellen.INSTANCE;
        //byte[] salt = saltErstellen.getNextSalt();
        //Todo: Salt in Datenbank reinschreiben
        //String saltInsert = "insert into M_USER (muname, salt) values (?)";
        //jdbcTemplate.execute(saltInsert);
        //saltedPw.append(Arrays.toString(salt));
        //saltedPw.append(new_mpwd);


        String hpwd = hashen256(saltedPw.toString());

        String sqlInsert = "insert into M_USER (muname,mpwd,salt) values (?,?,?)";
        int res = 0;
        try {
        	//execute the query and check exceptions
            res = jdbcTemplate.update(sqlInsert, new Object[] {new_uname, hpwd, saltDB}, new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
        } catch (DataAccessException e) {
            ModelAndView mv = returnToRegister(session);
            //throw new SuperFatalAndReallyAnnoyingException(String.format("Sorry but %sis a bad grammar or has following problem %s", sqlInsert, e.getMessage()));
        }

        //Register Ok
        //Do Autologin

            if (res > 0) {
                ModelAndView mv = new ModelAndView("redirect:login.secu");
                mv.addObject("msg", "You've been successfully registered, please login:");
                Integer token = getNewToken();
                mv.addObject("csrfToken", token);
                Cookie loginCookie = new Cookie("login", String.valueOf(token));
                response.addCookie(loginCookie);
                session.setAttribute("csrfToken", token);
                return mv;

            }
        //Error
        return returnToRegister(session);
    }

    private ModelAndView returnToRegister(HttpSession session) {
        //Ohhhhh not correct try again
        return new ModelAndView("redirect:register.secu");
    }

    private String hashen256(String mpwd) {
        String hpwd = null;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            digest.update(mpwd.getBytes(), 0, mpwd.length());

            hpwd = new BigInteger(1, digest.digest()).toString(16);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hpwd;
    }

    private boolean isUserInputValid(String mname) {
        return mname.matches("[A-Za-z0-9]+");
    }

    private boolean isLoginNameTaken(String mname) {
        String sql = String.format("select count(*) from M_USER where muname = '%s'", mname);
        int res=0;
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
