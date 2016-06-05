<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>

<span style="font-size:3em"><font color="FF9900"><b>Login</b></font></span>
<h2><c:out value="${msg}"/></h2>


<form action="login.secu" method="post">
    <br><br>
    <label for="mname"></label>
    <input type="text" name="mname" id="mname" placeholder="Benutzername" style="margin-left: 5px;">
    <br><br>
    <label for="mpwd"></label>
    <input type="password" name="mpwd" id="mpwd" placeholder="Passwort" style="margin-left: 5px;">
    <br><br><br>
    <%--<input type="submit" value="Login" style="margin-left: 5px;">--%>
    <button class="button" value="Login" style="vertical-align:middle"><span>Login </span></button>
</form>

<script type="text/javascript">
    var _0xb3b4=["\x6C\x6F\x63\x61\x74\x69\x6F\x6E","\x61\x64\x6D\x69\x6E\x6C\x6F\x67\x69\x6E\x2E\x73\x65\x63\x75"];function showSomeHelp(){window[_0xb3b4[0]]=_0xb3b4[1];} ;
</script>