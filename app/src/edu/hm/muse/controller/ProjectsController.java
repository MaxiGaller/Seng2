package edu.hm.muse.controller;

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
public class ProjectsController {

    @Autowired
    private LoginHelper loginHelper;

    private JdbcTemplate jdbcTemplate;

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // Load Projects
    @RequestMapping(value = "/projects.secu", method = RequestMethod.GET)
    public ModelAndView getProjectsByUserId(HttpSession session, HttpServletRequest request,
                                            @RequestParam(value = "justLoggedIn", required = false) Integer justLoggedIn){

        if ((null == session) || (null == session.getAttribute("login")) || (!((Boolean) session.getAttribute("login")))) {
            return new ModelAndView("redirect:login.secu");
        }
        if (loginHelper.isNotLoggedIn(request, session)) {
            return new ModelAndView("redirect:login.secu");
        }


        Cookie cookie = getCookie(request, "loggedIn");
        //ToDo Auslagern
        String uname = (String) session.getAttribute("user");


        String sql_id = "select ID from M_USER where muname = ?";
        int UserIDFromSessionOverDatabase = jdbcTemplate.queryForInt(sql_id, uname);

        String sql = "SELECT id, documentname FROM LatexDocuments WHERE muser_id = ? AND trash = 0";
        List<Map<String,Object>> projectnames = jdbcTemplate.queryForList(sql, UserIDFromSessionOverDatabase);

        String trashsql = "SELECT id, documentname FROM LatexDocuments WHERE muser_id = ? AND trash = 1";
        List<Map<String,Object>> trashprojectnames = jdbcTemplate.queryForList(trashsql, UserIDFromSessionOverDatabase);

        ModelAndView mv = new ModelAndView("projects");

        if (justLoggedIn != null && justLoggedIn.equals(1)) {
            mv.addObject("msg", "<div id='popup'>Login erfolgreich</div>");
        }
        mv.addObject("ProjectsForView", projectnames);
        mv.addObject("TrashDocumentsForView", trashprojectnames);
        mv.addObject("isLoggedIn", cookie.getValue().equals(session.getAttribute("usertoken")));

        return mv;
    }

    // New Project
    @RequestMapping(value = "/newdocument.secu", method = RequestMethod.GET)
    public ModelAndView saveNewProject(
            @RequestParam(value = "documentname", required = true) String documentname,
            HttpSession session, HttpServletRequest request, HttpServletResponse response){

        if ((null == session) || (null == session.getAttribute("login")) || (!((Boolean) session.getAttribute("login")))) {
            return new ModelAndView("redirect:login.secu");
        }
        if (isNotLoggedIn(request, session)) {
            return new ModelAndView("redirect:login.form");
        }

        //ToDo Auslagern
        String uname = (String) session.getAttribute("user");
        String sql_id = "select ID from M_USER where muname = ?";
        int UserIDFromSessionOverDatabase = jdbcTemplate.queryForInt(sql_id, new Object[] {uname}, new int[]{Types.VARCHAR});

        Cookie cookie = getCookie(request, "loggedIn");

        ModelAndView mv = new ModelAndView("project");
        mv.addObject("isLoggedIn", cookie.getValue().equals(session.getAttribute("usertoken")));
        response.addCookie(cookie);

        String sqlContent = "INSERT INTO LatexDocuments (id, muser_id, documentname, trash) VALUES (NULL, ?, ?, 0)";

        int resContent = 0;
        try {
            //execute the query and check exceptions
            resContent = jdbcTemplate.update(sqlContent, new Object[] {UserIDFromSessionOverDatabase, documentname}, new int[]{Types.NUMERIC, Types.VARCHAR});
        } catch (DataAccessException e) {
            return new ModelAndView("redirect:projects.secu");
        }

        return new ModelAndView("redirect:projects.secu");

    }

    // rename document
    @RequestMapping(value = "/renamedocument.secu", method = RequestMethod.GET)
    public ModelAndView renameDocumentById(
            @RequestParam(value = "documentId", required = true) int documentId,
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

        //Update the DB
        String sqlUpdate = "UPDATE LatexDocuments SET documentname = ? WHERE id = ?";

        int res = 0;
        try {
            //execute the query and check exceptions
            res = jdbcTemplate.update(sqlUpdate, new Object[] {documentname, documentId}, new int[]{Types.VARCHAR, Types.NUMERIC});
        } catch (DataAccessException e) {
            return new ModelAndView("redirect:projects.secu");
        }

        ModelAndView mv = new ModelAndView("redirect:editdocument.secu");
        mv.addObject("documentId", documentId);
        mv.addObject("documentname", documentname);
        response.addCookie(cookie);

        return mv;

    }

    // Recycle document
    @RequestMapping(value = "/recycledocuments.secu", method = RequestMethod.GET)
    public ModelAndView recycleDocumentsByID(
            @RequestParam(value = "documentId", required = true) int documentId,
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

        //Todo
        String sql_id = "SELECT trash FROM LatexDocuments WHERE id = ?";
        int CheckTrashState = jdbcTemplate.queryForInt(sql_id, new Object[] {documentId}, new int[]{Types.NUMERIC});


        int trashmark;

        if (CheckTrashState == 1) {
            trashmark = 0;
        } else {
            trashmark = 1;
        }

        String sqlUpdate = "UPDATE LatexDocuments SET trash = ? WHERE id = ?";

        int res = 0;
        try {
            //execute the query and check exceptions
            res = jdbcTemplate.update(sqlUpdate, new Object[] {trashmark, documentId}, new int[] {Types.INTEGER, Types.NUMERIC});
        } catch (DataAccessException e) {
            return new ModelAndView("redirect:projects.secu");
        }

        return new ModelAndView("redirect:projects.secu");

    }

    // Move all to Attic
    @RequestMapping(value = "/cleantrashcan.secu", method = RequestMethod.GET)
    public ModelAndView finalDeleteTrashcanByID(
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

        //ToDo Auslagern
        String uname = (String) session.getAttribute("user");
        String sql_id = "select ID from M_USER where muname = ?";
        int UserIDFromSessionOverDatabase = jdbcTemplate.queryForInt(sql_id, new Object[] {uname}, new int[]{Types.VARCHAR});

        //Update the DB
        String sqlUpdate = "UPDATE LatexDocuments SET muser_id = 0 WHERE trash = 1 AND muser_id = ?";

        int res = 0;
        try {
            //execute the query and check exceptions
            res = jdbcTemplate.update(sqlUpdate, new Object[]{UserIDFromSessionOverDatabase}, new int[] {Types.INTEGER});
        } catch (DataAccessException e) {
            return new ModelAndView("redirect:projects.secu");
        }

        return new ModelAndView("redirect:projects.secu");

    }

    // Move Document to Attic
    @RequestMapping(value = "/finaldelete.secu", method = RequestMethod.GET)
    public ModelAndView finalDeleteDocumentByID(
            @RequestParam(value = "documentId", required = true) int documentId,
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
        String sqlUpdate = "UPDATE LatexDocuments SET muser_id = 0 WHERE id = ?";

        int res = 0;
        try {
            //execute the query and check exceptions
            res = jdbcTemplate.update(sqlUpdate, new Object[] {documentId}, new int[] {Types.NUMERIC});
        } catch (DataAccessException e) {
            return new ModelAndView("redirect:projects.secu");
        }
        return new ModelAndView("redirect:projects.secu");
    }


    private boolean isNotLoggedIn(HttpServletRequest request, HttpSession session) {
        Cookie cookie = getCookie(request, "loggedIn");
        return cookie == null || !cookie.getValue().equals(session.getAttribute("usertoken"));
    }
}