<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>

<div class="m1">Benutzerkonto</div>
<br>
<div class="m2"> Hallo <b><c:out value="${userDomain.uname}"/></b></div>
<br><br>
<a href="internchange.secu"><button class="button schrift3 hoehe3" style="margin-left: 20px;"><span>Passwort <i class="fa fa-edit"></i></span></button></a>
