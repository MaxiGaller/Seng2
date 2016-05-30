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
					<input type="hidden" value="<c:out value="${ProjectsForView.id}"/>" name="documentId" id="documentId">
					<input type="hidden" value="<c:out value="${ProjectsForView.documentname}"/>" name="documentname" id="documentname">
				    <input type="submit" value="Bearbeiten">	
				</form>
			</td>
	        <td>
				<form action="recycledocuments.secu" method="get">
					<input type="hidden" value="<c:out value="${ProjectsForView.id}"/>" name="documentId" id="documentId">
				    <input type="submit" value="Papierkorb">	
				</form>
			</td>
	    </tr>
	</c:forEach>
</table>

<hr>

<h2>Neues Dokument anlegen</h2>
<form action="newdocument.secu" method="get">
	<table>
		<tr>  
			<td>
				<input type="text" value="" name="documentname" id="documentname">
			    <input type="submit" value="Neues Projekt anlegen">
			</td>
		</tr>
	</table>
</form>

<hr>

<h2>M&uuml;lleimer</h2>
<table>
	<c:forEach items="${TrashDocumentsForView}" var="TrashDocumentsForView">
	    <tr>
	    	<td>${TrashDocumentsForView.documentname}</td>
	        <td>
				<form action="recycledocuments.secu" method="get">
					<input type="hidden" value="<c:out value="${TrashDocumentsForView.id}"/>" name="documentId" id="documentId">
				    <input type="submit" value="Wiederherstellen">	
				</form>
			</td>
	        <td>
				<form action="finaldelete.secu" method="get">
					<input type="hidden" value="<c:out value="${TrashDocumentsForView.id}"/>" name="documentId" id="documentId">
				    <input type="submit" value="Endg&uuml;ltig l&ouml;schen">	
				</form>
			</td>
	    </tr>
	</c:forEach>
</table>

<jsp:include page="../help/acc_help.jsp"/>
<jsp:include page="../../foot.jsp"/>