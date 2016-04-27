package edu.hm.muse.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogoutController {
	
	
	// Proceed the Loggout
	@RequestMapping(value = "/logout.secu", method = RequestMethod.POST)
	public ModelAndView logoutAnUserByButton(@RequestParam(value = "logout", required = false) String logout, HttpSession session) {

		//paranoid check, just because java can
		if (!logout.isEmpty()) {
			//do the logout, destroy the session
            session.setAttribute("login", false);
        }
		
		//Go back to Intern - should show the Login
		return new ModelAndView("redirect:intern.secu");
	}
	
}
