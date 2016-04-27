package edu.hm.muse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HeadController {

	@RequestMapping(value = "../../head.secu", method = RequestMethod.POST)
    public ModelAndView showSomeHead() {
        ModelAndView mv = new ModelAndView("head");
        mv.addObject("message", "No input found");
        return mv;
    }
	
}
