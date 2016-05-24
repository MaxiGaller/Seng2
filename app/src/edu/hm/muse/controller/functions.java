package edu.hm.muse.controller;

import java.security.MessageDigest;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.hm.muse.domain.User;

public class functions {
	
	private JdbcTemplate jdbcTemplate;
	
	public ModelAndView getLogin(HttpSession session) {
		ModelAndView result = null;
		if ((null == session) || (null == session.getAttribute("login")) || ((Boolean) session.getAttribute("login") == false)) {
			result = new ModelAndView("redirect:login.secu");
	    }
		return result;
	}
	
//	public String getUser(HttpSession session){
//		if ((null == session) || (null == session.getAttribute("login")) || ((Boolean) session.getAttribute("login") == false)) {
//			return (String) session.getAttribute("user");
//		} 
//	}
    
	
	public int getUserID(final String uname) {
		//Return ID form User using UserMapper Function
        //Return ID form User using UserMapper Function
        String sql_id = String.format("select ID from M_USER where muname = '%s'", uname);
		int UserID = jdbcTemplate.queryForInt(sql_id);
        return UserID;
	}

}
