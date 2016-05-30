<%--@elvariable id="userDomain" type="edu.hm.muse.domain.User"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>

<span style="font-size:3em"><font color="FF9900"><b>LaTeX Editor</b></font></span>
<br><br>
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
<br>
<form action="newdocument.secu" method="get">
	<table>
		<tr>
			<td>
				<input type="text" value="" name="documentname" id="documentname">
				<input type="hidden" value="${projectId}" name="projectId" id="projectId">
				<input type="submit" value="Neues Projekt anlegen">
			</td>
		</tr>
	</table>
</form>