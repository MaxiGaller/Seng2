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
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Types;import edu.hm.muse.exception.SuperFatalAndReallyAnnoyingException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@Controller
public class RegisterController {

    private JdbcTemplate jdbcTemplate;

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @RequestMapping(value = "/register.secu", method = RequestMethod.GET)
    public ModelAndView showLoginScreen() {
        ModelAndView mv = new ModelAndView("register");
        mv.addObject("msg", "Neuen Benutzer anlegen. Bitte Usernamen und Passwort eintragen.");
        return mv;
    }

    @RequestMapping(value = "/register.secu", method = RequestMethod.POST)
    public ModelAndView doSomeRegister(@RequestParam(value = "new_uname", required = false) String new_uname, @RequestParam(value = "new_mpwd", required = false) String new_mpwd, HttpSession session) {
    	if (null == new_uname || null == new_mpwd || new_uname.isEmpty() || new_mpwd.isEmpty()) {
            throw new SuperFatalAndReallyAnnoyingException("I can not process, because the requestparam new_uname or new_mpwd is empty or null or something like this");
        }

        //This is the sql statement
        String sql = String.format("INSERT INTO `M_USER` (`ID`, `muname`, `mpwd`) VALUES ('1', '%s', '%s')", new_uname, new_mpwd);

        int res = 0;
        try {
            res = jdbcTemplate.queryForInt(sql);
        } catch (DataAccessException e) {
            throw new SuperFatalAndReallyAnnoyingException(String.format("Sorry but %s is a bad grammar or has following problem %s", sql, e.getMessage()));
        }

        //If there are any results, than the username and password is correct
        if (res > 0) {
            return new ModelAndView("redirect:login.secu");
        }
        //Ohhhhh not correct try again
        ModelAndView mv = returnToRegister(session);
        return mv;
    }

    private ModelAndView returnToRegister(HttpSession session) {
        //Ohhhhh not correct try again
        ModelAndView mv = returnToRegister(session);
        return mv;
    }

}
