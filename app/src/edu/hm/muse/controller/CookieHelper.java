package edu.hm.muse.controller;


import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CookieHelper {

    public void eraseCookiesLogout(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                cookie.setValue(null);
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
    }

    public void eraseCookieLogin(HttpServletRequest req, HttpServletResponse resp, String nameOfCookie) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(nameOfCookie)) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                }
            }
    }
}