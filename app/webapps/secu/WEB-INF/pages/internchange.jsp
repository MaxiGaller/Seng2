<%--@elvariable id="userDomain" type="edu.hm.muse.domain.User"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>

<span style="font-size: 50px; font-family: Arial; margin-left: 20px; color:#4169e1;"><b>Passwort &auml;ndern</b></span>
<br>
<span style="margin-left: 20px; font-size: 20px;">Hier kannst du dein Passwort &auml;ndern</span>
<br><br><br>

<form method="post" action="change.secu">
    <input type="hidden" name="uid" value="${userDomain.id}" >
    <input type="hidden" name="uname" value="${userDomain.uname}" id="uname">
    <%--<input type="password" name="upwd" id="upwd" placeholder="neues Passwort">--%>
    <input class="eingabefeld" type="password" name="upwd" id="upwd" placeholder="neues Passwort" style="margin-left: 20px;" autofocus>
    <br><br><br>
    <input type="image" src="img/floppy.gif"  value="Passwort &auml;ndern" style="margin-left: 20px;">
</form>
