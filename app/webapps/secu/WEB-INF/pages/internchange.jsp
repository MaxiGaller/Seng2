<%--@elvariable id="userDomain" type="edu.hm.muse.domain.User"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>

<span style="font-size: 50px; font-family: Arial; margin-left: 20px; color:#4169e1;"><b>Passwort &auml;ndern</b></span>
<br>
<span style="margin-left: 20px; font-size: 20px;">Hier kannst du dein Passwort &auml;ndern</span>
<br><br><br>

<h2>${msg}</h2>

<form action="change.secu" method="post" >
    <input type="hidden" name="uid" value="${userDomain.id}" >
    <input type="hidden" name="uname" value="${userDomain.uname}" id="uname">
    <%--<input type="password" name="upwd" id="upwd" placeholder="neues Passwort">--%>
    <label for="upwd"></label>
    <input class="eingabefeld" type="password" name="upwd" id="upwd" placeholder="neues Passwort" style="margin-left: 20px;" autofocus>
    <br>
    <label for="upwd1"></label>
    <input class="eingabefeld" type="password" name="upwd1" id="upwd1" placeholder="Passwort wiederholen" style="margin-left: 20px;">
    <br><br><br>

    <%--<input type="image" src="img/floppy.gif" style="margin-left: 20px;">--%>
    <button class="buttonKleinBreit" style="vertical-align:middle; margin-left: 20px;"><span>Passwort <i class="fa fa-save"></i></span></button>
</form>
