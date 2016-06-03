/*
 * **
 *  *                                        __          ____                                     __
 *  *     /'\_/`\                 __        /\ \        /\  _`\                                __/\ \__
 *  *    /\      \  __  __   ___ /\_\    ___\ \ \___    \ \,\L\_\     __    ___  __  __  _ __ /\_\ \ ,_\  __  __
 *  *    \ \ \__\ \/\ \/\ \/' _ `\/\ \  /'___\ \  _ `\   \/_\__ \   /'__`\ /'___\\ \/\ \/\`'__\/\ \ \ \/ /\ \/\ \
 *  *     \ \ \_/\ \ \ \_\ \\ \/\ \ \ \/\ \__/\ \ \ \ \    /\ \L\ \/\  __//\ \__/ \ \_\ \ \ \/ \ \ \ \ \_\ \ \_\ \
 *  *      \ \_\\ \_\ \____/ \_\ \_\ \_\ \____\\ \_\ \_\   \ `\____\ \____\ \____\ \____/\ \_\  \ \_\ \__\\/`____ \
 *  *       \/_/ \/_/\/___/ \/_/\/_/\/_/\/____/ \/_/\/_/    \/_____/\/____/\/____/\/___/  \/_/   \/_/\/__/ `/___/> \
 *  *                                                                                                         /\___/
 *  *                                                                                                         \/__/
 *  *
 *  *     ____                                               __          ____
 *  *    /\  _`\                                            /\ \        /\  _`\
 *  *    \ \ \L\ \     __    ____    __     __     _ __  ___\ \ \___    \ \ \L\_\  _ __  ___   __  __  _____
 *  *     \ \ ,  /   /'__`\ /',__\ /'__`\ /'__`\  /\`'__\'___\ \  _ `\   \ \ \L_L /\`'__\ __`\/\ \/\ \/\ '__`\
 *  *      \ \ \\ \ /\  __//\__, `\\  __//\ \L\.\_\ \ \/\ \__/\ \ \ \ \   \ \ \/, \ \ \/\ \L\ \ \ \_\ \ \ \L\ \
 *  *       \ \_\ \_\ \____\/\____/ \____\ \__/.\_\\ \_\ \____\\ \_\ \_\   \ \____/\ \_\ \____/\ \____/\ \ ,__/
 *  *        \/_/\/ /\/____/\/___/ \/____/\/__/\/_/ \/_/\/____/ \/_/\/_/    \/___/  \/_/\/___/  \/___/  \ \ \/
 *  *                                                                                                    \ \_\
 *  *    This file is part of BREW.
 *  *
 *  *    BREW is free software: you can redistribute it and/or modify
 *  *    it under the terms of the GNU General Public License as published by
 *  *    the Free Software Foundation, either version 3 of the License, or
 *  *    (at your option) any later version.
 *  *
 *  *    BREW is distributed in the hope that it will be useful,
 *  *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *    GNU General Public License for more details.
 *  *
 *  *    You should have received a copy of the GNU General Public License
 *  *    along with BREW.  If not, see <http://www.gnu.org/licenses/>.                                                                                                  \/_/
 *
 */

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
                                    HttpServletResponse response, HttpSession session,
                                    HttpServletRequest request) {
        if (null == mname || null == mpwd || mname.isEmpty() || mpwd.isEmpty()) {
            return new ModelAndView("redirect:login.secu?login=failed");
            //throw new SuperFatalAndReallyAnnoyingException("I can not process, because the requestparam mname or mpwd is empty or null or something like this");
        }

        if (!isUserInputValid(mname)) {
            ModelAndView mv = new ModelAndView("login");
            mv.addObject("msg", "Nur Buchstaben und Zahlen sind erlaubt!!");
            return mv;
        }
        if (!isLoginNameTaken(mname)) {
            return new ModelAndView("redirect:login.secu");
        }

        String getSalt = "select salt from M_USER where muname = ?";
        String salt = jdbcTemplate.queryForObject(getSalt, new Object[]{mname}, String.class);

        StringBuilder saltedPw = new StringBuilder();
        saltedPw.append(salt);
        saltedPw.append(mpwd);


        String hpwd = hashen256(saltedPw.toString());

        //This is the sql statement
        String sql = "select count(*) from M_USER where muname = ? and mpwd = ?";

        int res = 0;
        try {
            res = jdbcTemplate.queryForInt(sql,new Object[] {mname, hpwd}, new int[]{Types.VARCHAR, Types.VARCHAR});

            if (session.getAttribute("csrfToken") == null || getCookie(request, "login") == null) {
                return new ModelAndView("redirect:login.secu");
            }

            int csrfTokenFromSession = (int) session.getAttribute("csrfToken");
            int csrfTolenFromCookie = Integer.parseInt(getCookie(request, "login").getValue());
            if (csrfTokenFromSession != 0 && csrfTolenFromCookie != 0 && res > 0) {
                if (csrfTolenFromCookie == csrfTokenFromSession) {
                    int token = getNewToken();
                    session.setAttribute("user", mname);
                    Cookie loginCookie = new Cookie("loggedIn", String.valueOf(token));
                    cookieHelper.eraseCookie(request, response, "login");
                    response.addCookie(loginCookie);
                    session.setAttribute("usertoken", String.valueOf(token));
                    session.setAttribute("login", true);
                    session.removeAttribute("csrftoken");
                    return new ModelAndView("redirect:projects.secu?justLoggedIn=1");
                }
            }

        } catch (DataAccessException e) {
            throw new SuperFatalAndReallyAnnoyingException(String.format("Sorry but %sis a bad grammar or has following problem %s", sql, e.getMessage()));
        }

        //Ohhhhh not correct try again
        ModelAndView mv = returnToLogin(session);
        return mv;
    }


    private ModelAndView returnToAdminLogin(HttpSession session) {
        //Ohhhhh not correct try again
        ModelAndView mv = new ModelAndView("redirect:adminlogin.secu");
        mv.addObject("msg", "Sorry try again");
        session.setAttribute("login", false);
        return mv;
    }

    private ModelAndView returnToLogin(HttpSession session) {
        //Ohhhhh not correct try again
        ModelAndView mv = new ModelAndView("redirect:login.secu");
        mv.addObject("msg", "Sorry try again");
        session.invalidate();
        return mv;
    }

    public static String calculateSHA256(InputStream is) {
        String output;
        int read;
        byte[] buffer = new byte[8192];
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
            byte[] hash = digest.digest();
            BigInteger bigInt = new BigInteger(1, hash);
            output = bigInt.toString(16);
        }
        catch (Exception e) {
            e.printStackTrace( System.err );
            return "0";
        }
        return output;
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
        String sql = "select count(*) from M_USER where muname = ?";
        int res=0;
        try {
            res = jdbcTemplate.queryForInt(sql, new Object[] {mname}, new int[] {Types.VARCHAR});
            if (res > 0) {
                return true;
            }
        } catch (DataAccessException e) {
            throw new SuperFatalAndReallyAnnoyingException(String.format("Sorry but %sis a bad grammar or has following problem %s", sql, e.getMessage()));
        }
        return false;
    }

    private int getNewToken() {
        SecureRandom random = new SecureRandom();
        return random.nextInt();
    }


}
