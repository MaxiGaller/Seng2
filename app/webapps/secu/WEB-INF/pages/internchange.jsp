<%--@elvariable id="userDomain" type="edu.hm.muse.domain.User"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<span style="font-size:3em"><font color="FF9900"><b>Passwort &auml;ndern</b></font></span>
<br>
<b>Hier kannst du dein Passwort &auml;ndern</b>
<br><br><br>
<form method="post" action="change.secu">
    <input type="hidden" name="uid" value="${userDomain.id}" >
    <input type="text" name="uname" value="${userDomain.uname}" id="uname">
    <label for="uname">Name</label>
    <input type="password" name="upwd" value="${userDomain.upwd}" id="upwd">
    <label for="upwd">Password</label>
    <input type="submit" value="Passwort &auml;ndern">
</form>