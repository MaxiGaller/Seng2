<%--@elvariable id="userDomain" type="edu.hm.muse.domain.User"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>The LaTeX Editor</h1>

<table>
	<c:forEach items="${ProjectsForView}" var="ProjectsForView">
	    <tr>
	        <td>${ProjectsForView.documentname}</td>
	        <td>
				<form action="editdocument.secu" method="get">
					<input type="hidden" value="<c:out value="${ProjectsForView.id}"/>" name="projectId" id="projectId">
					<input type="hidden" value="<c:out value="${ProjectsForView.documentname}"/>" name="documentname" id="documentname">
				    <input type="submit" value="Bearbeiten">	
				</form>
			</td>
			<!--
	        <td>
				<form action="delete.secu" method="get">
					<input type="hidden" value="<c:out value="${ProjectsForView.id}"/>" name="projectId" id="projectId">
					<input type="hidden" value="<c:out value="${ProjectsForView.documentname}"/>" name="documentname" id="documentname">
				    <input type="submit" value="Löschen">	
				</form>
			</td>
			-->
	    </tr>
	</c:forEach>
</table>

<jsp:include page="../help/acc_help.jsp"/>
<jsp:include page="../../foot.jsp"/>