<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>

<div class="m1">Login</div>

<div class="m3"><c:out value="${msg}"/></div>

<form action="login.secu" method="post">
    <br><br>
    <input class="eingabefeld" type="text" name="mname" placeholder="Benutzername" style="margin-left: 20px;" autofocus>
    <br><br>
    <input class=eingabefeld type="password" name="mpwd" placeholder="Passwort" style="margin-left: 20px;">
    <br><br><br>
    <button class="button" style="margin-left: 20px;"><span>Login</span></button>
</form>