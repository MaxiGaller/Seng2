package edu.hm.muse.controller;

import java.security.MessageDigest;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.hm.muse.domain.User;
import edu.hm.muse.exception.SuperFatalAndReallyAnnoyingException;

@Controller
public class ProjectsController {
	
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	// Load Project
	@RequestMapping(value = "/projects.secu", method = RequestMethod.GET)
	public ModelAndView getProjectsByUserId(HttpSession session){
		
        if ((null == session) || (null == session.getAttribute("login")) || ((Boolean) session.getAttribute("login") == false)) {
            return new ModelAndView("redirect:login.secu");
        }

        String uname = (String) session.getAttribute("user");
        String sql_id = String.format("select ID from M_USER where muname = '%s'", uname);
		int UserIDFromSessionOverDatabase = jdbcTemplate.queryForInt(sql_id);
        String sql = "SELECT id, documentname FROM LatexDocuments WHERE muser_id = ?";
        List<Map<String,Object>> projectnames = jdbcTemplate.queryForList(sql, UserIDFromSessionOverDatabase);
        
        ModelAndView mv = new ModelAndView("projects");
        
        mv.addObject("ProjectsForView", projectnames);
        
        return mv;
        
	}
	
	
}
