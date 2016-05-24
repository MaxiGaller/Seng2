package edu.hm.muse.controller;

import java.security.MessageDigest;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
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
public class EditDocumentController {
	
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	// Load Document
	@RequestMapping(value = "/editdocument.secu", method = RequestMethod.GET)
	public ModelAndView getSnipedsByProjectID(
			@RequestParam(value = "projectId", required = true) int projectId,
			@RequestParam(value = "documentname", required = true) String documentname,
			HttpSession session){
		
        if ((null == session) || (null == session.getAttribute("login")) || ((Boolean) session.getAttribute("login") == false)) {
            return new ModelAndView("redirect:login.secu");
        }
        
//        String sql = "SELECT * FROM LatexSniped JOIN LatexType WHERE LatexSniped.project_id = ? AND LatexSniped.content_type LIKE LatexType.id";
        String sql = "SELECT * FROM LatexSniped WHERE project_id = ?";
        List<Map<String,Object>> projectSnipeds = jdbcTemplate.queryForList(sql, projectId);
        
        String sqlTypes = "SELECT * FROM LatexType";
        List<Map<String,Object>> projectTypes = jdbcTemplate.queryForList(sqlTypes);
        
        ModelAndView mv = new ModelAndView("editdocument");
        
        mv.addObject("projectId", projectId);
        mv.addObject("documentname", documentname);
        mv.addObject("TypesForView", projectTypes);
        mv.addObject("SnipedsForView", projectSnipeds);
        
        return mv;
        
	}
	
	// Edit Sniped
	@RequestMapping(value = "/editsniped.secu", method = RequestMethod.GET)
	public ModelAndView editSnipedBySnipedID(
			@RequestParam(value = "projectId", required = true) int projectId,
			@RequestParam(value = "documentname", required = true) String documentname,
			HttpSession session){
		
        if ((null == session) || (null == session.getAttribute("login")) || ((Boolean) session.getAttribute("login") == false)) {
            return new ModelAndView("redirect:login.secu");
        }
        
//        String sql = "SELECT * FROM LatexSniped JOIN LatexType WHERE LatexSniped.project_id = ? AND LatexSniped.content_type LIKE LatexType.id";
        String sql = "SELECT * FROM LatexSniped WHERE project_id = ?";
        List<Map<String,Object>> projectSnipeds = jdbcTemplate.queryForList(sql, projectId);
        
        String sqlTypes = "SELECT * FROM LatexType";
        List<Map<String,Object>> projectTypes = jdbcTemplate.queryForList(sqlTypes);
        
        ModelAndView mv = new ModelAndView("editdocument");
        
        mv.addObject("documentname", documentname);
        mv.addObject("TypesForView", projectTypes);
        mv.addObject("SnipedsForView", projectSnipeds);
        
        return mv;
        
	}
	

	// New Sniped
	@RequestMapping(value = "/newsniped.secu", method = RequestMethod.GET)
	public ModelAndView saveNewSniped(
			@RequestParam(value = "projectId", required = true) int projectId,
			@RequestParam(value = "content_type", required = true) int content_type,
			@RequestParam(value = "snipedContent", required = true) String snipedContent,
			HttpSession session){
		
        if ((null == session) || (null == session.getAttribute("login")) || ((Boolean) session.getAttribute("login") == false)) {
            return new ModelAndView("redirect:login.secu");
        }
        
        //ToDo Auslagern
        String uname = (String) session.getAttribute("user");
        String sql_id = String.format("select ID from M_USER where muname = '%s'", uname);
		int UserIDFromSessionOverDatabase = jdbcTemplate.queryForInt(sql_id);
        
		//Select the Last ID from the Table
    	String sqlSelect = "SELECT id FROM LatexSniped WHERE project_id = ? ORDER BY id DESC LIMIT 1";
        int lastId = jdbcTemplate.queryForInt(sqlSelect, projectId);
        //Increment the last ID
        lastId++;
        
        //Insert the Content to DB
        String sqlInsert = String.format("INSERT INTO `LatexSniped` (`id`, `muser_id`, `project_id`, `content`, `content_type`) VALUES ('%s', '%s', '%s', '%s', '%s')", lastId, UserIDFromSessionOverDatabase, projectId, snipedContent, content_type);

        int res = 0;
        try {
        	//execute the query and check exceptions
            res = jdbcTemplate.update(sqlInsert);
        } catch (DataAccessException e) {
            throw new SuperFatalAndReallyAnnoyingException(String.format("Sorry but %sis a bad grammar or has following problem %s", sqlInsert, e.getMessage()));
        }
        
        return new ModelAndView("redirect:editdocument.secu");
        
	}
	
	
}
