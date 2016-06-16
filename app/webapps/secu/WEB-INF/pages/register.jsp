<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>

<div class="m1">Registrieren</div>

<div class="m3"><c:out value="${msg}"/></div>

<form action="register.secu" method="post">
    <br><br>
    <input class="eingabefeld" type="text" name="new_uname" placeholder="neuer Benutzername" style="margin-left: 20px;" autofocus >
    <br><br>
    <input class="eingabefeld" type="password" name="new_mpwd" placeholder="neues Passwort" style="margin-left: 20px;">
    <br>
    <input class="eingabefeld" type="password" name="new_mpwd1" placeholder="Passwort wiederholen" style="margin-left: 20px;">
    <br><br><br>
    <button class="button" style="margin-left: 20px;"><span>Registrieren</span></button>
</form>
