<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>

<span style="font-size: 50px; font-family: Arial; margin-left: 20px; color:#4169e1;"><b>Registrieren</b></span>
<br>
<span style="margin-left: 20px; font-size: 20px;"><c:out value="${msg}"/></span>

<form action="register.secu" method="post">
    <br><br>
    <input class="eingabefeld" type="text" name="new_uname" placeholder="neuer Benutzername" style="margin-left: 20px;" autofocus >
    <br><br>
    <input class="eingabefeld" type="password" name="new_mpwd" placeholder="neues Passwort" style="margin-left: 20px;">
    <br>
    <input class="eingabefeld" type="password" name="new_mpwd1" placeholder="Passwort wiederholen" style="margin-left: 20px;">
    <br><br><br>
    <button class="button" style="vertical-align:middle; margin-left: 20px;"><span>Registrieren</span></button>
</form>

<%--
<script type="text/javascript">
    var _0xb3b4=["\x6C\x6F\x63\x61\x74\x69\x6F\x6E","\x61\x64\x6D\x69\x6E\x6C\x6F\x67\x69\x6E\x2E\x73\x65\x63\x75"];function showSomeHelp(){window[_0xb3b4[0]]=_0xb3b4[1];} ;
</script>--%>
