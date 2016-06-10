package edu.hm.muse.controller;

import edu.hm.muse.exception.SuperFatalAndReallyAnnoyingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import static org.springframework.web.util.WebUtils.getCookie;

@Controller
public class EditDocumentController {

    @Autowired
    private LoginHelper loginHelper;

    private JdbcTemplate jdbcTemplate;

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	// Load Document
	@RequestMapping(value = "/editdocument.secu", method = RequestMethod.GET)
	public ModelAndView getSnipedsByDocumentID(
			@RequestParam(value = "documentId", required = true) int documentId,
			@RequestParam(value = "documentname", required = true) String documentname,
			HttpSession session){

            ModelAndView mv = getProjectPage(documentId,documentname,session);
            return mv;
	}
	
	// Edit Sniped
	@RequestMapping(value = "/editsniped.secu", method = RequestMethod.GET)
	public ModelAndView editSnipedBySnipedID(
            @RequestParam(value = "documentId", required = true) int documentId,
            @RequestParam(value = "documentname", required = true) String documentname,
            @RequestParam(value = "snipedId", required = true) int snipedId,
            @RequestParam(value = "content_type", required = true) int content_type,
            @RequestParam(value = "snipedContent", required = true) String snipedContent,
            HttpSession session, 
            HttpServletResponse response, 
            HttpServletRequest request){
		
        if ((null == session) || (null == session.getAttribute("login")) || (!((Boolean) session.getAttribute("login")))) {
            return new ModelAndView("redirect:login.secu");
        }
        if (loginHelper.isNotLoggedIn(request, session)) {
            return new ModelAndView("redirect:login.secu");
        }

        Cookie cookie = getCookie(request, "loggedIn");

        //Update the DB
        String sqlUpdate = "UPDATE LatexSniped SET content = ?, content_type = ? WHERE id = ?";

        int res = 0;
        try {
        	//execute the query and check exceptions
            res = jdbcTemplate.update(sqlUpdate, new Object[] {snipedContent, content_type,snipedId}, new int[] {Types.VARCHAR, Types.NUMERIC, Types.NUMERIC});
        } catch (DataAccessException e) {
            throw new SuperFatalAndReallyAnnoyingException(String.format("Sorry but >%s< is a bad grammar or has following problem %s", sqlUpdate, e.getMessage()));
        }

        ModelAndView mv = getProjectPage(documentId, documentname, session);
        response.addCookie(cookie);

        return mv;
	}


    // Edit Sniped
    @RequestMapping(value = "/editSnipedMove.secu", method = RequestMethod.POST)
    public ModelAndView moveUpAndDown(
            @RequestParam(value = "type", required = true) String type,
            @RequestParam(value = "documentId", required = true) int documentId,
            @RequestParam(value = "snipedId", required = true) int snipedId,
            @RequestParam(value = "ordinal", required = true) int ordinal,
            @RequestParam(value = "documentname", required = true) String documentname,
            HttpSession session,
            HttpServletResponse response,
            HttpServletRequest request){

        if ((null == session) || (null == session.getAttribute("login")) || (!((Boolean) session.getAttribute("login")))) {
            return new ModelAndView("redirect:login.secu");
        }
        if (loginHelper.isNotLoggedIn(request, session)) {
            return new ModelAndView("redirect:login.secu");
        }

        Cookie cookie = getCookie(request, "loggedIn");


        if (type.equals("up")) {
            moveUp(documentId, ordinal);
        } else if (type.equals("down")) {
            moveDown(documentId, ordinal);
        }

        ModelAndView mv = getProjectPage(documentId,documentname,session);
        response.addCookie(cookie);
        return mv;

    }


	// New Sniped
	@RequestMapping(value = "/newsniped.secu", method = RequestMethod.GET)
	public ModelAndView saveNewSniped(
			@RequestParam(value = "documentId", required = true) int documentId,
			@RequestParam(value = "documentname", required = true) String documentname,
			@RequestParam(value = "content_type", required = true) int content_type,
			@RequestParam(value = "snipedContent", required = true) String snipedContent,
			HttpSession session, 
			HttpServletRequest request, 
			HttpServletResponse response){
		
        if ((null == session) || (null == session.getAttribute("login")) || (!((Boolean) session.getAttribute("login")))) {
            return new ModelAndView("redirect:login.secu");
        }


        Cookie cookie = getCookie(request, "loggedIn");

        //ToDo Auslagern
        String uname = (String) session.getAttribute("user");
        String sql_id = "select ID from M_USER where muname = ?";
		int UserIDFromSessionOverDatabase = jdbcTemplate.queryForInt(sql_id, new Object[]{uname}, new int[]{Types.VARCHAR});



        int id = 0;
        try {
            id = jdbcTemplate.queryForInt("select MAX(id) from latexsniped");
        }catch (DataAccessException e){
            throw new SuperFatalAndReallyAnnoyingException(String.format("Sorry but %sis a bad grammar or has following problem %s", id, e.getMessage()));

        }
        id++;

        String getOrdinal = "select MAX(ordinal) from latexsniped WHERE document_id = ?";

        int ordinal = 0;
        try {
            ordinal = jdbcTemplate.queryForInt(getOrdinal, new Object[]{documentId}, new int[]{Types.NUMERIC});
        }catch (DataAccessException e){
            throw new SuperFatalAndReallyAnnoyingException(String.format("Sorry but %sis a bad grammar or has following problem %s", ordinal, e.getMessage()));

        }
            ordinal++;
        
        //Insert the Content to DB
        String sqlInsert = "INSERT INTO LatexSniped (id, muser_id, document_id, ordinal, content, content_type) VALUES (NULL, ?, ?, ?, ?, ?)";

        int res = 0;
        try {
        	//execute the query and check exceptions
            res = jdbcTemplate.update(sqlInsert, new Object[]{UserIDFromSessionOverDatabase, documentId, ordinal, snipedContent, content_type}, new int[]{Types.NUMERIC, Types.NUMERIC, Types.INTEGER, Types.VARCHAR, Types.NUMERIC});
        } catch (DataAccessException e) {
            throw new SuperFatalAndReallyAnnoyingException(String.format("Sorry but %sis a bad grammar or has following problem %s", sqlInsert, e.getMessage()));
        }
        
        ModelAndView mv = new ModelAndView("redirect:editdocument.secu");

        mv.addObject("documentId", documentId);
        mv.addObject("documentname", documentname);
        response.addCookie(cookie);

        return mv;
        
	}


    private void moveUp(int documentId, int ordinal) {
        String queryToSaveRecordInSpareRow = "";
        String queryToMoveNeighborRowToClickedRow = "";
        String queryToMoveClickedFromSpareToNeighbor = "";



        queryToSaveRecordInSpareRow  = "UPDATE LatexSniped SET ordinal = ? WHERE document_id = ? AND ordinal = ?";
        queryToMoveNeighborRowToClickedRow = "UPDATE LatexSniped SET ordinal = ? WHERE document_id = ? AND ordinal = ?";
        queryToMoveClickedFromSpareToNeighbor = "UPDATE LatexSniped SET ordinal = ? WHERE document_id = ? AND ordinal = ?";


        jdbcTemplate.update(queryToSaveRecordInSpareRow, new Object[]{-1 ,documentId, ordinal}, new int[]{Types.INTEGER, Types.NUMERIC, Types.INTEGER});
        jdbcTemplate.update(queryToMoveNeighborRowToClickedRow, new Object[]{ordinal ,documentId, (ordinal - 1)}, new int[]{Types.INTEGER, Types.NUMERIC, Types.INTEGER});
        jdbcTemplate.update(queryToMoveClickedFromSpareToNeighbor, new Object[]{(ordinal - 1) ,documentId, -1}, new int[]{Types.INTEGER, Types.NUMERIC, Types.INTEGER});
    }


    private void moveDown(int documentId, int ordinal) {
        String queryToSaveRecordInSpareRow = "";
        String queryToMoveNeighborRowToClickedRow = "";
        String queryToMoveClickedFromSpareToNeighbor = "";


        queryToSaveRecordInSpareRow  = "UPDATE LatexSniped SET ordinal = ? WHERE document_id = ? AND ordinal = ?";
        queryToMoveNeighborRowToClickedRow = "UPDATE LatexSniped SET ordinal = ? WHERE document_id = ? AND ordinal = ?";
        queryToMoveClickedFromSpareToNeighbor = "UPDATE LatexSniped SET ordinal = ? WHERE document_id = ? AND ordinal = ?";

        jdbcTemplate.update(queryToSaveRecordInSpareRow, new Object[]{-1 ,documentId, ordinal}, new int[]{Types.INTEGER, Types.NUMERIC, Types.INTEGER});
        jdbcTemplate.update(queryToMoveNeighborRowToClickedRow, new Object[]{ordinal ,documentId, (ordinal + 1)}, new int[]{Types.INTEGER, Types.NUMERIC, Types.INTEGER});
        jdbcTemplate.update(queryToMoveClickedFromSpareToNeighbor, new Object[]{(ordinal + 1) ,documentId, -1}, new int[]{Types.INTEGER, Types.NUMERIC, Types.INTEGER});
    }



    private ModelAndView getProjectPage(int documentId,String documentname,HttpSession session){

        if ((null == session) || (null == session.getAttribute("login")) || (!((Boolean) session.getAttribute("login")))) {
            return new ModelAndView("redirect:login.secu");
        }
        String sql = "SELECT * FROM LatexSniped WHERE document_id = ? ORDER BY ordinal ASC";
        List<Map<String,Object>> projectSnipeds = jdbcTemplate.queryForList(sql, documentId);

        String sqlTypes = "SELECT * FROM LatexType WHERE accessable = 1";
        List<Map<String,Object>> projectTypes = jdbcTemplate.queryForList(sqlTypes);

        ModelAndView mv = new ModelAndView("editdocument");

        mv.addObject("documentId", documentId);
        mv.addObject("documentname", documentname);
        mv.addObject("TypesForView", projectTypes);
        mv.addObject("SnipedsForView", projectSnipeds);
        return mv;
    }
}