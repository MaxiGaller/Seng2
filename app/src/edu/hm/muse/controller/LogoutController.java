package edu.hm.muse.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogoutController {

	@RequestMapping(value = "/logout.secu", method = RequestMethod.POST)
	public ModelAndView logoutAnUserByButton(
			@RequestParam(value = "logout", required = false) String logout, 
			HttpSession session) {

		if (!logout.isEmpty()) {
            session.setAttribute("login", false);
        }
		
		return new ModelAndView("redirect:intern.secu");
	}
	
}
