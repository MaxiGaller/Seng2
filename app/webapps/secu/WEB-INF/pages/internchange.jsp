<%--@elvariable id="userDomain" type="edu.hm.muse.domain.User"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<span style="font-size:3em"><font color="FF9900"><b>Passwort &auml;ndern</b></font></span>
<br>
<b>Hier kannst du dein Passwort &auml;ndern</b>
<br>

<form method="post" action="change.secu">
    <input type="hidden" name="uid" value="${userDomain.id}" >
    <input type="hidden" name="uname" value="${userDomain.uname}" id="uname">
    <br><br>
    <input type="password" name="upwd" id="upwd" placeholder="neues Passwort">
    <br><br>
    <%--TODO Funktion fehlt--%>
    <input type="text"   placeholder="Passwort wiederholen">
    <br><br>
    <input type="image" src="img/floppy.gif"  value="Passwort &auml;ndern">
</form>


<%--falls nix mehr geht--%>
<%--
<form method="post" action="change.secu">
    <input type="hidden" name="uid" value="${userDomain.id}" >
    <input type="text" name="uname" value="${userDomain.uname}" id="uname">
    <br><br>
    <input type="password" name="upwd" id="upwd" placeholder="neues Passwort">
    <br><br>
    <input type="submit" value="Passwort &auml;ndern">
</form>--%>
