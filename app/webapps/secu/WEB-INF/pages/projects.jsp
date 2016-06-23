<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>

<div class="m1">LaTeX Editor</div>

<span style="margin-left: 80px; font-size: 20px; font-family: 'Comic Sans MS'; font-style: italic"><c:out value="${msg}"/></span>
<br><br>

<span style="margin-left: 20px; font-size: 25px;">Ihre Projekte</span>
<table>
	<c:forEach items="${DocumentsForView}" var="DocumentsForView">
		<tr>
			<td><span style="margin-left: 20px;"><c:out value="${DocumentsForView.documentname}"/></span></td>
			<td>
				<form action="editdocument.secu" method="get">
					<input type="hidden" value="<c:out value="${DocumentsForView.id}"/>" name="documentId">
					<input type="hidden" value="<c:out value="${DocumentsForView.documentname}"/>" name="documentname" >
					<input type="hidden" value="<c:out value="${DocumentsForView.documentauthor}"/>" name="documentauthor">
					<input type="hidden" value="write" name="mode">
					<button class="button schrift3 hoehe3 grau" value="Bearbeiten"><span>Bearbeiten Modus</span></button>
				</form>
			</td>
			<td>
				<form action="editdocument.secu" method="get">
					<input type="hidden" value="<c:out value="${DocumentsForView.id}"/>" name="documentId">
					<input type="hidden" value="<c:out value="${DocumentsForView.documentname}"/>" name="documentname">
					<input type="hidden" value="<c:out value="${DocumentsForView.documentauthor}"/>" name="documentauthor">
					<input type="hidden" value="read" name="mode">
					<button class="button schrift3 hoehe3 grau"><span>Lesen Modus</span></button>
				</form>
			</td>
			<td>
				<form action="editdocument.secu" method="get">
					<input type="hidden" value="<c:out value="${DocumentsForView.id}"/>" name="documentId">
					<input type="hidden" value="<c:out value="${DocumentsForView.documentname}"/>" name="documentname">
					<input type="hidden" value="<c:out value="${DocumentsForView.documentauthor}"/>" name="documentauthor">
					<input type="hidden" value="showlatex" name="mode">
					<button class="button schrift3 hoehe3 grau" value="Bearbeiten"><span>LaTeX Modus</span></button>
				</form>
			</td>
			<td>
				<form action="recycledocuments.secu" method="get">
					<input type="hidden" value="<c:out value="${DocumentsForView.id}"/>" name="documentId">
					<button class="button breite4 schrift3 hoehe4 hellRot" value="Papierkorb"><span><i class="fa fa-trash w3-large"></i></span></button>
				</form>
			</td>
		</tr>
	</c:forEach>
</table>

<hr>

<span style="margin-left: 20px; font-size: 25px;">Freigebene Projekte</span>
<table>
	<c:forEach items="${contributorDocuments}" var="contributorDocuments">
		<tr>
			<td><span style="margin-left: 20px;"><c:out value="${contributorDocuments.documentname}"/></span></td>
			<td>
				<form action="editdocument.secu" method="get">
					<input type="hidden" value="<c:out value="${contributorDocuments.document_id}"/>" name="documentId">
					<input type="hidden" value="<c:out value="${contributorDocuments.documentname}"/>" name="documentname">
					<input type="hidden" value="<c:out value="${contributorDocuments.documentauthor}"/>" name="documentauthor">
					<input type="hidden" value="write" name="mode">
					<button class="button schrift3 hoehe3 grau" value="Bearbeiten"><span>Bearbeiten Modus</span></button>
				</form>
			</td>
			<td>
				<form action="editdocument.secu" method="get">
					<input type="hidden" value="<c:out value="${contributorDocuments.document_id}"/>" name="documentId">
					<input type="hidden" value="<c:out value="${contributorDocuments.documentname}"/>" name="documentname">
					<input type="hidden" value="<c:out value="${contributorDocuments.documentauthor}"/>" name="documentauthor">
					<input type="hidden" value="read" name="mode">
					<button class="button schrift3 hoehe3 grau" value="Bearbeiten"><span>Lesen Modus</span></button>
				</form>
			</td>
			<td>
				<form action="editdocument.secu" method="get">
					<input type="hidden" value="<c:out value="${contributorDocuments.document_id}"/>" name="documentId">
					<input type="hidden" value="<c:out value="${contributorDocuments.documentname}"/>" name="documentname">
					<input type="hidden" value="<c:out value="${contributorDocuments.documentauthor}"/>" name="documentauthor">
					<input type="hidden" value="showlatex" name="mode">
					<button class="button schrift3 hoehe3 grau" value="Bearbeiten"><span>LaTeX Modus</span></button>
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
				<input class="eingabefeld" type="text" name="documentname" placeholder="Name des Dokuments" style="margin-left: 20px;">
				<button class="button schrift3 hoehe3 hellBlau" value="Neues Projekt anlegen"><span>Neues Projekt <i class="fa fa-save"></i></span></button>
			</td>
		</tr>
	</table>
</form>

<hr>

<span style="margin-left: 20px; font-size: 25px;">M&uuml;lleimer</span>
<form action="cleantrashcan.secu" method="get">
	<button class="button breite1 schrift2 hoehe2 schwarz" value="Papierkorb leeren" style="margin-left: 20px;"><span><b>Papierkorb leeren <i class="fa fa-trash w3-large"></i></b></span></button>
</form>
<br>
<table>
	<c:forEach items="${TrashDocumentsForView}" var="TrashDocumentsForView">
		<tr>
			<td><span style="margin-left: 20px;"><c:out value="${TrashDocumentsForView.documentname}"/></span></td>
			<td>
				<form action="recycledocuments.secu" method="get">
					<input type="hidden" value="<c:out value="${TrashDocumentsForView.id}"/>" name="documentId">
					<div style="float: right;"><button class="button schrift3 hoehe3 grau" value="Wiederherstellen"><span>Wiederherstellen</span></button></div>
				</form>
			</td>
			<td>
				<form action="finaldelete.secu" method="get">
					<input type="hidden" value="<c:out value="${TrashDocumentsForView.id}"/>" name="documentId">
					<div style="float: right; margin-right: 60%"><button class="button schrift3 hoehe3 hellRot" value="Endg&uuml;ltig l&ouml;schen"><span>Endg&uuml;ltig l&ouml;schen</span></button></div>
				</form>
			</td>
		</tr>
	</c:forEach>
</table>

<hr>

<span style="margin-left: 20px; font-size: 25px;">Globale Snippeds</span>
<table>
	<form action="newglobalsniped.secu" method="get">
		<tr>
			<td>
				<span style="margin-left: 20px;"><textarea rows="4" cols="50" name="snipedGlobalContent" placeholder="neues Snipped"></textarea></span>
			</td>
			<td>
				<div style="float: left; margin-right: 60%"><select name="Global_content_type">
					<c:forEach items="${AllContentTypes}" var="AllContentTypes">
						<option value="${AllContentTypes.id}">${AllContentTypes.type}</option>
					</c:forEach>
				</select></div>
				<div style="float: left; margin-right: 60%"><button class="button breite4 schrift3 hoehe4 hellBlau" value="Neu Anlegen"><span><i class="fa fa-save"></i></span></button></div>
			</td>
		</tr>
	</form>
</table>
<br><br><br>
<table>
	<c:forEach items="${GlobalSnipedsForView}" var="GlobalSnipedsForView">
		<form action="editGlobalSnipeds.secu" method="get">
			<tr>
				<td>
					<span style="margin-left: 20px;"><textarea rows="4" cols="50" name="GlobalSniped_content">${GlobalSnipedsForView.content}</textarea></span>
					<input type="hidden" value="<c:out value="${GlobalSnipedsForView.id}"/>" name="GlobalSniped_id">
				</td>
				<td>
					<select name="GlobalSniped_content_type">
						<c:forEach items="${AllContentTypes}" var="AllContentTypes">
							<c:if test="${AllContentTypes.id == GlobalSnipedsForView.content_type}">
								<option value="${AllContentTypes.id}" selected>${AllContentTypes.type}</option>
							</c:if>
							<c:if test="${AllContentTypes.id != GlobalSnipedsForView.content_type}">
								<option value="${AllContentTypes.id}">${AllContentTypes.type}</option>
							</c:if>
						</c:forEach>
					</select>
					<button class="button breite4 schrift3 hoehe4 hellBlau" value="Speichern"><span><i class="fa fa-save"></i></span></button>
				</td>
		</form>
		<form action="CopyGlobalSnipedToDocument.secu" method="get">
			<td>
				<select name="documentId">
					<c:forEach items="${DocumentsForView}" var="DocumentsForView">
						<option value="${DocumentsForView.id}"><c:out value="${DocumentsForView.documentname}"/></option>
					</c:forEach>
				</select>
			</td>
			<td>
				<input type="hidden" value="<c:out value="${GlobalSnipedsForView.id}"/>" name="GlobalSniped_id">
				<input type="hidden" value="<c:out value="${GlobalSnipedsForView.content_type}"/>" name="GlobalSniped_content_type">
				<button class="button schrift3 hoehe3 grau" value="Kopieren"><span>Zu Dok kopieren</span></button>
			</td>
		</form>
		</tr>
	</c:forEach>
</table>

<hr>

<span style="margin-left: 20px; font-size: 25px;">Mitarbeiter Verwaltung</span>
<table>
	<tr>
		<form action="invitecontributor.secu" method="get">
			<td>
				<span style="margin-left: 20px;"><select name="ContribteDocument">
					<c:forEach items="${DocumentsForView}" var="DocumentsForView">
						<option value="${DocumentsForView.id}">${DocumentsForView.documentname}</option>
					</c:forEach>
				</select></span>
				<select name="ContributeUser">
					<c:forEach items="${Contributors}" var="Contributors">
						<option value="${Contributors.id}">${Contributors.muname}</option>
					</c:forEach>
				</select>
				<div style="margin-left: 20px;"><button class="button schrift3 hoehe3 grau" value="Speichern"><span>Einladen</span></button></div>
			</td>
		</form>
	</tr>
</table>
<br><br>
<table>
	<c:forEach items="${SavedContributors}" var="SavedContributors">
		<tr>
			<td>
				<span style="margin-left: 20px;"><c:forEach items="${DocumentsForView}" var="DocumentsForView">
					<c:forEach items="${Contributors}" var="Contributors">
						<c:if test="${Contributors.id == SavedContributors.contribute_muser_id}">
							<c:if test="${DocumentsForView.id == SavedContributors.document_id}">
								${Contributors.muname} - ${DocumentsForView.documentname}
							</c:if>
						</c:if>
					</c:forEach>
				</c:forEach></span>
			</td>
			<td>
				<form action="removecontributor.secu" method="get">
					<input type="hidden" value="<c:out value="${SavedContributors.id}"/>" name="contribute_id">
					<div style="float: right; margin-right: 70%"><button class="button schrift3 hoehe3 hellRot" value="Entfernen"><span>Entfernen <i class="fa fa-trash w3-large"></i></span></button></div>
				</form>
			</td>
		</tr>
	</c:forEach>
</table>