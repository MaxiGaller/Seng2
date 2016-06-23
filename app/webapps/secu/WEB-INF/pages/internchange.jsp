<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>

<div class="m1">Passwort &auml;ndern</div>

<div class="m3">Hier kannst du dein Passwort &auml;ndern</div>

<div class="m4"><c:out value="${msg}"/></div>
<br>
<form action="change.secu" method="post" >
    <%--<input type="hidden" name="uid" value="${userDomain.id}" >--%>
    <input type="hidden" name="uname" value="${userDomain.uname}" id="uname">
    <input class="eingabefeld" type="password" name="upwd" placeholder="neues Passwort" style="margin-left: 20px;" autofocus>
    <br>
    <input class="eingabefeld" type="password" name="upwd1" placeholder="Passwort wiederholen" style="margin-left: 20px;">
    <br><br><br>
    <button class="button schrift3 hoehe3 hellBlau" style="margin-left: 20px;"><span>Passwort <i class="fa fa-save"></i></span></button>
</form>
