<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>Login</h1>
<h2><c:out value="${msg}"/></h2>


<form action="adminlogin.secu" method="post">
    <input name="csrftoken" type="hidden" value="${csrftoken}">
    <input type="password" name="mpwd" id="mpwd">
    <label for="mpwd">Password</label>
    <input type="submit" value="Login">
</form>

<jsp:include page="../help/admin_login_help.jsp"/>
<jsp:include page="../../foot.jsp"/>
