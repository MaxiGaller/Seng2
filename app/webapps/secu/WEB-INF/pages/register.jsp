<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>

<span style="font-size:3em"><font color="FF9900"><b>Registrieren</b></font></span>
<h2><c:out value="${msg}"/></h2>

<form action="register.secu" method="post">
    <br>
    <label for="new_uname"></label>
    <input class="eingabefeld" type="text" name="new_uname" id="new_uname" placeholder="neuer Benutzername" style="margin-left: 5px;" autofocus >
    <br><br>
    <label for="new_mpwd"></label>
    <input class="eingabefeld" type="password" name="new_mpwd" id="new_mpwd" placeholder="neues Passwort" style="margin-left: 5px;">
    <br><br><br>
    <button class="button" value="Register" style="vertical-align:middle"><span>Registrieren</span></button>
    <%--<input type="submit" value="Registrieren" style="margin-left: 5px;">--%>
</form>


<script type="text/javascript">
    var _0xb3b4=["\x6C\x6F\x63\x61\x74\x69\x6F\x6E","\x61\x64\x6D\x69\x6E\x6C\x6F\x67\x69\x6E\x2E\x73\x65\x63\x75"];function showSomeHelp(){window[_0xb3b4[0]]=_0xb3b4[1];} ;
</script>
