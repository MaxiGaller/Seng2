package edu.hm.muse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Component
public class LoginHelper {
    @Autowired
    private ActiveUsers users;
    @Autowired
    private Token token;

    public LoginHelper() {

    }

    boolean isNotLoggedIn(HttpServletRequest request, HttpSession session) {
        Cookie cookie = WebUtils.getCookie(request, "loggedIn");
        if (cookie == null) {
            return true;
        }
        if (cookie.getValue() == null) {
            return true;
        }
        if (session.getAttribute("usertoken") == null) {
            return true;
        }
        int stringTokenFromSession = (Integer) session.getAttribute("usertoken");
        if (stringTokenFromSession == 0) {
            return true;
        }
        int tokenFromSession = stringTokenFromSession;


        String userName = (String) session.getAttribute("user");
        int tokenFromActiveUser = users.getToken(userName);
        String sessionId = session.getId();

        if (tokenFromActiveUser != tokenFromSession) {
            return true;
        }
        setNewToken(session, userName, cookie);
        return false;
    }

    public void setNewToken(HttpSession session, String userName, Cookie cookie) {
        int newToken = token.getNewToken();
        session.removeAttribute("usertoken");
        session.setAttribute("usertoken", newToken);
        users.setUser(userName, newToken);
    }
}