<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>

<span style="font-size: 50px; font-family: Arial; margin-left: 20px; color:#4169e1;"><b>Login</b></span>
<br>
<span style="margin-left: 20px; font-size: 20px;"><c:out value="${msg}"/></span>

<form action="login.secu" method="post">
    <br><br>
    <input class="eingabefeld" type="text" name="mname" placeholder="Benutzername" style="margin-left: 20px;" autofocus>
    <br><br>
    <input class=eingabefeld type="password" name="mpwd" placeholder="Passwort" style="margin-left: 20px;">
    <br><br><br>
    <button class="button" style="vertical-align:middle; margin-left: 20px;"><span>Login</span></button>
</form>