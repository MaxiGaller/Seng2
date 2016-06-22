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
// Author Maximilian Galler
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

//      String sql = "SELECT * FROM LatexDocuments d INNER JOIN LatexDocumentContributors c ON c.document_id = d.id AND d.trash = 0 AND (d.muser_id = ? OR c.contribute_muser_id = ?)";
        String sql = "SELECT id, documentname, documentauthor FROM LatexDocuments WHERE muser_id = ? AND trash = 0";
        List<Map<String,Object>> documentNames = jdbcTemplate.queryForList(sql, UserIDFromSessionOverDatabase);

        String sqlContributor = "SELECT * FROM LatexDocumentContributors c INNER JOIN LatexDocuments d ON c.document_id = d.id  WHERE owner_muser_id != ? AND contribute_muser_id = ? AND trash = 0";
        List<Map<String,Object>> contributorDocuments = jdbcTemplate.queryForList(sqlContributor, UserIDFromSessionOverDatabase, UserIDFromSessionOverDatabase);

        String trashsql = "SELECT id, documentname FROM LatexDocuments WHERE muser_id = ? AND trash = 1";
        List<Map<String,Object>> trashDocumentNames = jdbcTemplate.queryForList(trashsql, UserIDFromSessionOverDatabase);

        String sqlAllContentTypes = "SELECT * FROM LatexType";
        List<Map<String,Object>> AllContentTypes = jdbcTemplate.queryForList(sqlAllContentTypes);

        String sqlGlobalSnipeds = "SELECT * FROM LatexGlobalSniped WHERE muser_id = ?";
        List<Map<String,Object>> GlobalSnipeds = jdbcTemplate.queryForList(sqlGlobalSnipeds, UserIDFromSessionOverDatabase);

        String sqlContributors = "SELECT * FROM M_USER WHERE id != ? AND id != 0";
        List<Map<String,Object>> Contributors = jdbcTemplate.queryForList(sqlContributors, UserIDFromSessionOverDatabase);

        String sqlSavedContributors = "SELECT * FROM LatexDocumentContributors WHERE owner_muser_id = ?";
        List<Map<String,Object>> SavedContributors = jdbcTemplate.queryForList(sqlSavedContributors, UserIDFromSessionOverDatabase);

        ModelAndView mv = new ModelAndView("projects");

        if (justLoggedIn != null && justLoggedIn.equals(1)) {
            mv.addObject("msg", "Login erfolgreich");
        }

        mv.addObject("DUMP", sqlContributor);
        mv.addObject("SavedContributors", SavedContributors);
        mv.addObject("contributorDocuments", contributorDocuments);
        mv.addObject("Contributors", Contributors);
        mv.addObject("AllContentTypes", AllContentTypes);
        mv.addObject("GlobalSnipedsForView", GlobalSnipeds);
        mv.addObject("DocumentsForView", documentNames);
        mv.addObject("TrashDocumentsForView", trashDocumentNames);
        mv.addObject("isLoggedIn", cookie.getValue().equals(session.getAttribute("usertoken")));

        return mv;
    }

    // New Document
// Author Maximilian Galler
    @RequestMapping(value = "/newdocument.secu", method = RequestMethod.GET)
    public ModelAndView saveNewProject(
            @RequestParam(value = "documentname", required = true) String documentname,
            HttpSession session, HttpServletRequest request, HttpServletResponse response){

        if (loginHelper.isNotLoggedIn(request, session)) {
            return new ModelAndView("redirect:login.secu");
        }

        if ((null == session) || (null == session.getAttribute("login")) || (!((Boolean) session.getAttribute("login")))) {
            return new ModelAndView("redirect:login.secu");
        }

        if (documentname.replaceAll("[A-Za-z0-9 ]", "").length() > 0) {
            return new ModelAndView("redirect:projects.secu");
        }

//ToDo Auslagern
        String uname = (String) session.getAttribute("user");
        String sql_id = "select ID from M_USER where muname = ?";
        int UserIDFromSessionOverDatabase = jdbcTemplate.queryForInt(sql_id, new Object[] {uname}, new int[]{Types.VARCHAR});

        Cookie cookie = getCookie(request, "loggedIn");

        ModelAndView mv = new ModelAndView("project");
        mv.addObject("isLoggedIn", cookie.getValue().equals(session.getAttribute("usertoken")));
        response.addCookie(cookie);

        String sqlContent = "INSERT INTO LatexDocuments (id, muser_id, documentname, documentauthor, trash) VALUES (NULL, ?, ?, ?, 0)";

        int resContent = 0;
        try {
//execute the query and check exceptions
            resContent = jdbcTemplate.update(sqlContent, new Object[] {UserIDFromSessionOverDatabase, documentname, uname}, new int[]{Types.NUMERIC, Types.VARCHAR, Types.VARCHAR});
        } catch (DataAccessException e) {
            return new ModelAndView("redirect:projects.secu");
        }

        return new ModelAndView("redirect:projects.secu");

    }

    // New Invite Contributor
// Author Maximilian Galler
    @RequestMapping(value = "/invitecontributor.secu", method = RequestMethod.GET)
    public ModelAndView inviteContributor(
            @RequestParam(value = "ContribteDocument", required = true) String ContribteDocument,
            @RequestParam(value = "ContributeUser", required = true) String ContributeUser,
            HttpSession session, HttpServletRequest request, HttpServletResponse response){

        if (loginHelper.isNotLoggedIn(request, session)) {
            return new ModelAndView("redirect:login.secu");
        }

        if ((null == session) || (null == session.getAttribute("login")) || (!((Boolean) session.getAttribute("login")))) {
            return new ModelAndView("redirect:login.secu");
        }

//ToDo Auslagern
        String uname = (String) session.getAttribute("user");
        String sql_id = "select ID from M_USER where muname = ?";
        int UserIDFromSessionOverDatabase = jdbcTemplate.queryForInt(sql_id, new Object[] {uname}, new int[]{Types.VARCHAR});

/*String sqlDuplicateContributeUser = "SELECT id FROM LatexDocumentContributors WHERE contribute_muser_id = ? AND document_id = ?";
int resultDuplicateContributeUser = jdbcTemplate.queryForInt(sqlDuplicateContributeUser, new Object[] {ContributeUser, ContribteDocument}, new int[]{Types.NUMERIC, Types.NUMERIC});
*/
        Cookie cookie = getCookie(request, "loggedIn");

        ModelAndView mv = new ModelAndView("project");
        mv.addObject("isLoggedIn", cookie.getValue().equals(session.getAttribute("usertoken")));

/*if (resultDuplicateContributeUser != 0) {
mv.addObject("msg", "<div id='popup'>doppelt</div>");
return new ModelAndView("redirect:projects.secu");
}*/

        String sqlContent = "INSERT INTO LatexDocumentContributors (id, owner_muser_id, contribute_muser_id, document_id) VALUES (NULL, ?, ?, ?)";

        int resContent = 0;
        try {
//execute the query and check exceptions
            resContent = jdbcTemplate.update(sqlContent, new Object[] {UserIDFromSessionOverDatabase, ContributeUser, ContribteDocument}, new int[]{Types.NUMERIC, Types.NUMERIC, Types.NUMERIC});
        } catch (DataAccessException e) {
            return new ModelAndView("redirect:projects.secu");
        }

        return new ModelAndView("redirect:projects.secu");

    }

    // Remove Contributor
// Author Maximilian Galler
    @RequestMapping(value = "/removecontributor.secu", method = RequestMethod.GET)
    public ModelAndView removeContributorByID(
            @RequestParam(value = "contribute_id", required = true) int contribute_id,
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

        String sqlUpdate = "DELETE FROM LatexDocumentContributors WHERE id = ?";

        int res = 0;
        try {
//execute the query and check exceptions
            res = jdbcTemplate.update(sqlUpdate, new Object[] {contribute_id}, new int[] {Types.NUMERIC});
        } catch (DataAccessException e) {
            return new ModelAndView("redirect:projects.secu");
        }

        return new ModelAndView("redirect:projects.secu");

    }

    // New Global Sniped
// Author Maximilian Galler
    @RequestMapping(value = "/newglobalsniped.secu", method = RequestMethod.GET)
    public ModelAndView saveNewGlobalSniped(
            @RequestParam(value = "Global_content_type", required = true) int Global_content_type,
            @RequestParam(value = "snipedGlobalContent", required = true) String snipedGlobalContent,
            HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response){

        if (loginHelper.isNotLoggedIn(request, session)) {
            return new ModelAndView("redirect:login.secu");
        }

        if ((null == session) || (null == session.getAttribute("login")) || (!((Boolean) session.getAttribute("login")))) {
            return new ModelAndView("redirect:login.secu");
        }

        Cookie cookie = getCookie(request, "loggedIn");

        String uname = (String) session.getAttribute("user");
        String sql_id = "select ID from M_USER where muname = ?";
        int UserIDFromSessionOverDatabase = jdbcTemplate.queryForInt(sql_id, new Object[]{uname}, new int[]{Types.VARCHAR});

        String sqlInsert = "INSERT INTO LatexGlobalSniped (id, muser_id, content, content_type) VALUES (NULL, ?, ?, ?)";

        int resContent = 0;
        try {
//execute the query and check exceptions
            resContent = jdbcTemplate.update(sqlInsert, new Object[]{UserIDFromSessionOverDatabase, snipedGlobalContent, Global_content_type}, new int[]{Types.NUMERIC, Types.VARCHAR, Types.NUMERIC});
        } catch (DataAccessException e) {
            return new ModelAndView("redirect:projects.secu");
        }

        return new ModelAndView("redirect:projects.secu");

    }

    // Edit an Saved Global Sniped
// Author Maximilian Galler
    @RequestMapping(value = "/editGlobalSnipeds.secu", method = RequestMethod.GET)
    public ModelAndView editSnipedBySnipedID(
            @RequestParam(value = "GlobalSniped_id", required = true) int GlobalSniped_id,
            @RequestParam(value = "GlobalSniped_content_type", required = true) int GlobalSniped_content_type,
            @RequestParam(value = "GlobalSniped_content", required = true) String GlobalSniped_content,
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
        String sqlUpdate = "UPDATE LatexGlobalSniped SET content = ?, content_type = ? WHERE id = ?";

        int res = 0;
        try {
//execute the query and check exceptions
            res = jdbcTemplate.update(sqlUpdate, new Object[] {GlobalSniped_content, GlobalSniped_content_type, GlobalSniped_id}, new int[] {Types.VARCHAR, Types.NUMERIC, Types.NUMERIC});
        } catch (DataAccessException e) {
            return new ModelAndView("redirect:projects.secu");
        }

        return new ModelAndView("redirect:projects.secu");
    }

    // rename document
// Author Maximilian Galler
    @RequestMapping(value = "/renamedocument.secu", method = RequestMethod.GET)
    public ModelAndView renameDocumentById(
            @RequestParam(value = "documentId", required = true) int documentId,
            @RequestParam(value = "documentname", required = true) String documentname,
            @RequestParam(value = "mode", required = false) String mode,
            @RequestParam(value = "documentauthor", required = true) String documentauthor,
            HttpSession session,
            HttpServletResponse response,
            HttpServletRequest request){


        if ((null == session) || (null == session.getAttribute("login")) || (!((Boolean) session.getAttribute("login")))) {
            return new ModelAndView("redirect:login.secu");
        }
        if (loginHelper.isNotLoggedIn(request, session)) {
            return new ModelAndView("redirect:login.secu");
        }

        if (documentname.replaceAll("[A-Za-z0-9 ]", "").length() != 0) {
            return new ModelAndView("redirect:projects.secu");
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
        mv.addObject("documentauthor", documentauthor);
        mv.addObject("mode", mode);
        response.addCookie(cookie);

        return mv;

    }

    // set document author
// Author Maximilian Galler
    @RequestMapping(value = "/setauthor.secu", method = RequestMethod.GET)
    public ModelAndView setDocumentAuthorById(
            @RequestParam(value = "documentId", required = true) int documentId,
            @RequestParam(value = "documentname", required = true) String documentname,
            @RequestParam(value = "mode", required = false) String mode,
            @RequestParam(value = "documentauthor", required = true) String documentauthor,
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
        String sqlUpdate = "UPDATE LatexDocuments SET documentauthor = ? WHERE id = ?";

        int res = 0;
        try {
//execute the query and check exceptions
            res = jdbcTemplate.update(sqlUpdate, new Object[] {documentauthor, documentId}, new int[]{Types.VARCHAR, Types.NUMERIC});
        } catch (DataAccessException e) {
            return new ModelAndView("redirect:projects.secu");
        }

        ModelAndView mv = new ModelAndView("redirect:editdocument.secu");
        mv.addObject("documentId", documentId);
        mv.addObject("documentname", documentname);
        mv.addObject("documentauthor", documentauthor);
        mv.addObject("mode", mode);
        response.addCookie(cookie);

        return mv;

    }


    // Recycle document
// Author Maximilian Galler
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
// Author Maximilian Galler
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
// Author Maximilian Galler
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

        String uname = (String) session.getAttribute("user");
        String sql_id = "select ID from M_USER where muname = ?";
        int UserIDFromSessionOverDatabase = jdbcTemplate.queryForInt(sql_id, new Object[]{uname}, new int[]{Types.VARCHAR});

        if (!isUserInDocument(UserIDFromSessionOverDatabase, documentId)) {
            if (!isUserContributor(UserIDFromSessionOverDatabase, documentId)) {
                return new ModelAndView("redirect:projects.secu");
            }
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

    public boolean isUserInDocument (int userID, int documentID) {
        String sql = "SELECT Count(*) FROM LatexDocuments where muser_id = ? and id = ?";
        int res = 0;
        try {
            res = jdbcTemplate.queryForInt(sql, userID, documentID);
        } catch (DataAccessException e) {

        }
        return res > 0;
    }

    public boolean isUserContributor (int userID, int documentID) {
        String sql = "SELECT Count(*) FROM LatexDocumentContributors where contribute_muser_id = ? and document_id = ?";
        int res = 0;
        try {
            res = jdbcTemplate.queryForInt(sql, userID, documentID);
        } catch (DataAccessException e) {

        }
        return res > 0;
    }

}