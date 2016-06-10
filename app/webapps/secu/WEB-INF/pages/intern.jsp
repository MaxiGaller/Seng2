<%--@elvariable id="userDomain" type="edu.hm.muse.domain.User"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>

<span style="font-size: 50px; font-family: Arial; margin-left: 20px; color:#4169e1;"><b>Benutzerkonto</b></span>

<%--<div id="id">
    Deine ID lautet: <c:out value="${userDomain.id}"/>
</div>--%>

<%--<div id="uname">
    Dein Benutzername lautet: <c:out value="${userDomain.uname}"/>
</div>--%>
<br><br>
<span style="margin-left: 20px; font-size: 25px;"> Hallo <b><c:out value="${userDomain.uname}"/></b></span>
<br><br><br>
<%--<a href="internchange.secu">Passwort &auml;ndern</a>--%>
<a href="internchange.secu"</a><button class="buttonKleinBreit" a href="internchange.secu" style="vertical-align:middle; margin-left: 20px;"><span>Passwort <i class="fa fa-edit"></i></span></button>

