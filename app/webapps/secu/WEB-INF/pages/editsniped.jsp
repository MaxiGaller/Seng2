<%--@elvariable id="userDomain" type="edu.hm.muse.domain.User"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>Sniped bearbeiten</h1>

<table>
	<c:forEach items="${SnipedsForView}" var="SnipedsForView">
		<form action="editsniped.secu" method="get">
	    <tr>      
	        <td>
				<input type="text" value="<c:out value="${SnipedsForView.content}"/>" name="snipedContent" id="snipedContent">
			    <input type="submit" value="Speichern">	
			</td>
	    </tr>
	    </form>
	</c:forEach>
</table>

<jsp:include page="../help/acc_help.jsp"/>
<jsp:include page="../../foot.jsp"/>