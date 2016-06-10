package edu.hm.muse.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

public class functions {
	
	private JdbcTemplate jdbcTemplate;
	
	public ModelAndView getLogin(HttpSession session) {
		ModelAndView result = null;
		if ((null == session) || (null == session.getAttribute("login")) || (!((Boolean) session.getAttribute("login")))) {
			result = new ModelAndView("redirect:login.secu");
	    }
		return result;
	}

	
	public int getUserID(final String uname) {
		String sql_id = String.format("select ID from M_USER where muname = '%s'", uname);
		return jdbcTemplate.queryForInt(sql_id);
	}

}
