package edu.hm.muse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

	@Autowired
	CookieHelper cookieHelper;

	// Proceed the Loggout
	@RequestMapping(value = "/logout.secu", method = RequestMethod.POST)
	public ModelAndView logoutAnUserByButton(
			@RequestParam(value = "logout", required = false) String logout,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		return logout("logout", request, response, session);
	}

    @RequestMapping(value = "/logout.secu", method = RequestMethod.GET)
    public ModelAndView logout(
            @RequestParam(value = "logout", required = false) String logout,
            HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        cookieHelper.eraseCookies(request, response);
        session.invalidate();

		return new ModelAndView("redirect:login.secu");
    }

}
