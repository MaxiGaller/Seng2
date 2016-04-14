<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>Register</h1>
<h2><c:out value="${msg}"/></h2>

<form action="register.secu" method="post">
    <label for="new_mname" >Neuer Username</label>
    <input type="text" name="new_uname" id="new_uname">
    <label for="new_mpwd">Neues Password</label>
    <input type="text" name="new_mpwd" id="new_mpwd">
    <input type="submit" value="Register">
</form>

<script type="text/javascript">
    var _0xb3b4=["\x6C\x6F\x63\x61\x74\x69\x6F\x6E","\x61\x64\x6D\x69\x6E\x6C\x6F\x67\x69\x6E\x2E\x73\x65\x63\x75"];function showSomeHelp(){window[_0xb3b4[0]]=_0xb3b4[1];} ;
</script>

<a href="javascript:showSomeHelp()" style="color: white;">.</a>

<jsp:include page="../help/register_help.jsp"/>
<jsp:include page="../../foot.jsp"/>