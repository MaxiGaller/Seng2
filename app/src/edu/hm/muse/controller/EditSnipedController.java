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
public class EditSnipedController {
	
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	// Load Project
	@RequestMapping(value = "/editsniped.secu", method = RequestMethod.GET)
	public ModelAndView getSnipedByProjectID(
			@RequestParam(value = "snipedId", required = true) int snipedId,
			HttpSession session){
		
        if ((null == session) || (null == session.getAttribute("login")) || ((Boolean) session.getAttribute("login") == false)) {
            return new ModelAndView("redirect:login.secu");
        }
        			  
//        String sql = "SELECT * FROM `LatexSniped` JOIN `LatexType` WHERE `LatexSniped`.`id` = ? AND `LatexSniped`.`content_type` LIKE `LatexType`.`id`";
        String sql = "SELECT * FROM LatexSniped WHERE LatexSniped.id = ?";
        List<Map<String,Object>> projectOneSniped = jdbcTemplate.queryForList(sql, snipedId);
        
        ModelAndView mv = new ModelAndView("editdocument");
        
        mv.addObject("SnipedOneForView", projectOneSniped);
        
        return mv;
        
	}
	
	
}
