<%--@elvariable id="userDomain" type="edu.hm.muse.domain.User"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>

<span style="font-size:3em"><font color="FF9900"><b>Benutzerkonto</b></font></span>

<%--<div id="id">
    Deine ID lautet: <c:out value="${userDomain.id}"/>
</div>--%>

<%--<div id="uname">
    Dein Benutzername lautet: <c:out value="${userDomain.uname}"/>
</div>--%>
<br><br>
Hallo <b><c:out value="${userDomain.uname}"/></b>
<br><br>
<a href="internchange.secu">Passwort &aumlndern</a>