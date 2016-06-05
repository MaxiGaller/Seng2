<%--@elvariable id="userDomain" type="edu.hm.muse.domain.User"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<span style="font-size:3em"><font color="FF9900"><b>LaTeX Editor</b></font></span>
<br>
<h2>${msg}</h2>
<br><br>

<table>
	<c:forEach items="${ProjectsForView}" var="ProjectsForView">
	    <tr>
	        <td>${ProjectsForView.documentname}</td>
	        <td>
				<form action="editdocument.secu" method="get">
					<input type="hidden" value="<c:out value="${ProjectsForView.id}"/>" name="documentId" id="documentId">
					<input type="hidden" value="<c:out value="${ProjectsForView.documentname}"/>" name="documentname" id="documentname">
				    <%--<input type="submit" value="Bearbeiten">--%>
					<button class="button" value="Bearbeiten" style="vertical-align:middle"><span>Bearbeiten</span></button>
				</form>
			</td>
	        <td>
				<form action="recycledocuments.secu" method="get">
					<input type="hidden" value="<c:out value="${ProjectsForView.id}"/>" name="documentId" id="documentId">
				    <%--<input type="submit" value="Papierkorb">--%>
					<button class="button" value="Papierkorb" style="vertical-align:middle"><span>Papierkorb</span></button>
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
				<input type="text" value="" name="documentname" id="documentname" placeholder="Name des Dokuments">
			    <%--<input type="submit" value="Neues Projekt anlegen">--%>
				<button class="button" value="Neues Projekt anlegen" style="vertical-align:middle"><span>Neues Projekt anlegen</span></button>
			</td>
		</tr>
	</table>
</form>

<hr>

<h2>M&uuml;lleimer</h2>

<form action="cleantrashcan.secu" method="get">
	<%--<input type="submit" value="Papierkorb leeren">--%>
	<button class="button" value="Papierkorb leeren" style="vertical-align:middle"><span>Papierkorb leeren</span></button>
</form>

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
