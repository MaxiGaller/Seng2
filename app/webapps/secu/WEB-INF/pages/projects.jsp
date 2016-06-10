<%--@elvariable id="userDomain" type="edu.hm.muse.domain.User"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
<span style="font-size: 50px; font-family: Arial; margin-left: 20px; color:#4169e1;"><b>LaTeX Editor</b></span>

<br>
<h2>${msg}</h2>
<br><br>

<table>
	<c:forEach items="${ProjectsForView}" var="ProjectsForView">
		<tr>

			<td><c:out value="${ProjectsForView.documentname}"/></td>
			<td>
				<form action="editdocument.secu" method="get">
					<input type="hidden" value="<c:out value="${ProjectsForView.id}"/>" name="documentId" id="documentId">
					<input type="hidden" value="<c:out value="${ProjectsForView.documentname}"/>" name="documentname" id="documentname">
						<%--<input type="submit" value="Bearbeiten">--%>
					<button class="buttonKleinSchmal" value="Bearbeiten" style="vertical-align:middle"><span><i class="fa fa-edit"></i></span></button>
				</form>
			</td>
			<td>
				<form action="recycledocuments.secu" method="get">
					<input type="hidden" value="<c:out value="${ProjectsForView.id}"/>" name="documentId" id="documentId">
						<%--<input type="submit" value="Papierkorb">--%>
					<button class="buttonKleinSchmal" value="Papierkorb" style="vertical-align:middle"><span><i class="fa fa-trash w3-large"></i></span></button>
				</form>
			</td>
		</tr>
	</c:forEach>
</table>

<hr>

<span style="margin-left: 20px; font-size: 25px;">Neues Dokument anlegen</span>
<form action="newdocument.secu" method="get">
	<table>
		<tr>
			<td>
				<br>
				<input class="eingabefeld" type="text" value="" name="documentname" id="documentname" placeholder="Name des Dokuments" style="margin-left: 20px;">
				<%--<input type="submit" value="Neues Projekt anlegen">--%>
				<button class="buttonKleinBreit" value="Neues Projekt anlegen" style="vertical-align:middle"><span>Neues Projekt <i class="fa fa-save"></i></span></button>
			</td>
		</tr>
	</table>
</form>

<hr>

<h2>M&uuml;lleimer</h2>

<form action="cleantrashcan.secu" method="get">
	<%--<input type="submit" value="Papierkorb leeren">--%>
	<button class="buttonKleinSchwarz" value="Papierkorb leeren" style="vertical-align:middle"><span><b>Papierkorb leeren</b></span></button>
</form>

<table>
	<c:forEach items="${TrashDocumentsForView}" var="TrashDocumentsForView">
		<tr>
			<td>${TrashDocumentsForView.documentname}</td>
			<td>
				<form action="recycledocuments.secu" method="get">
					<input type="hidden" value="<c:out value="${TrashDocumentsForView.id}"/>" name="documentId" id="documentId">
						<%--<input type="submit" value="Wiederherstellen">--%>
					<button class="buttonKleinBreit" value="Wiederherstellen" style="vertical-align:middle"><span>Wiederherstellen</span></button>
				</form>
			</td>
			<td>
				<form action="finaldelete.secu" method="get">
					<input type="hidden" value="<c:out value="${TrashDocumentsForView.id}"/>" name="documentId" id="documentId">
						<%--<input type="submit" value="Endg&uuml;ltig l&ouml;schen">--%>
					<button class="buttonKleinBreit" value="Endg&uuml;ltig l&ouml;schen" style="vertical-align:middle"><span>Endg&uuml;ltig l&ouml;schen</span></button>
				</form>
			</td>
		</tr>
	</c:forEach>
</table>