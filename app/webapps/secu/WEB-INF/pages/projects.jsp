<%--@elvariable id="userDomain" type="edu.hm.muse.domain.User"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
<span style="font-size: 50px; font-family: Arial; margin-left: 20px; color:#4169e1;"><b>LaTeX Editor</b></span>

<br>
<h2>${msg}</h2>
<br><br>

<span style="margin-left: 20px; font-size: 25px;">Ihre Projekte</span>
<table>
	<c:forEach items="${DocumentsForView}" var="DocumentsForView">
		<tr>
			<td><c:out value="${DocumentsForView.documentname}"/></td>
			<td>
				<form action="editdocument.secu" method="get">
					<input type="hidden" value="<c:out value="${DocumentsForView.id}"/>" name="documentId" id="documentId">
					<input type="hidden" value="<c:out value="${DocumentsForView.documentname}"/>" name="documentname" id="documentname">
					<input type="hidden" value="<c:out value="${DocumentsForView.documentauthor}"/>" name="documentauthor" id="documentauthor">
					<input type="hidden" value="write" name="mode">
					<button class="buttonKleinBreit" value="Bearbeiten" style="vertical-align:middle">Bearbeiten Modus</button>
				</form>
			</td>
			<td>
				<form action="editdocument.secu" method="get">
					<input type="hidden" value="<c:out value="${DocumentsForView.id}"/>" name="documentId" id="documentId">
					<input type="hidden" value="<c:out value="${DocumentsForView.documentname}"/>" name="documentname" id="documentname">
					<input type="hidden" value="<c:out value="${DocumentsForView.documentauthor}"/>" name="documentauthor" id="documentauthor">
					<input type="hidden" value="read" name="mode">
					<button class="buttonKleinBreit" value="Bearbeiten" style="vertical-align:middle">Lesen Modus</button>
				</form>
			</td>
			<td>
				<form action="editdocument.secu" method="get">
					<input type="hidden" value="<c:out value="${DocumentsForView.id}"/>" name="documentId" id="documentId">
					<input type="hidden" value="<c:out value="${DocumentsForView.documentname}"/>" name="documentname" id="documentname">
					<input type="hidden" value="<c:out value="${DocumentsForView.documentauthor}"/>" name="documentauthor" id="documentauthor">
					<input type="hidden" value="showlatex" name="mode">
					<button class="buttonKleinBreit" value="Bearbeiten" style="vertical-align:middle">LaTeX Modus</button>
				</form>
			</td>
			<td>
				<form action="recycledocuments.secu" method="get">
					<input type="hidden" value="<c:out value="${DocumentsForView.id}"/>" name="documentId" id="documentId">
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

<span style="margin-left: 20px; font-size: 25px;">M&uuml;lleimer</span>
<form action="cleantrashcan.secu" method="get">
	<%--<input type="submit" value="Papierkorb leeren">--%>
	<button class="buttonKleinSchwarz" value="Papierkorb leeren" style="vertical-align:middle"><span><b>Papierkorb leeren</b></span></button>
</form>

<table>
	<c:forEach items="${TrashDocumentsForView}" var="TrashDocumentsForView">
		<tr>
			<td><c:out value="${TrashDocumentsForView.documentname}"/></td>
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

<hr>

<span style="margin-left: 20px; font-size: 25px;">Globale Snippeds</span>
<table>
	<form action="newglobalsniped.secu" method="get">
		<tr>
			<td>
				<textarea rows="4" cols="50" name="snipedGlobalContent" id="snipedGlobalContent"></textarea>
			</td>
			<td>
				<select name="Global_content_type">
					<c:forEach items="${AllContentTypes}" var="AllContentTypes">
						<option value="${AllContentTypes.id}">${AllContentTypes.type}</option>
					</c:forEach>
				</select>
				<button class="buttonKlein" value="Neu Anlegen" style="vertical-align:middle"><span>Speichern <i class="fa fa-save"></i></span></button>
			</td>
		</tr>
	</form>
</table>
<table>
	<c:forEach items="${GlobalSnipedsForView}" var="GlobalSnipedsForView">
	<form action="editGlobalSnipeds.secu" method="get">
		<tr>
			<td>
				<textarea rows="4" cols="50" name="GlobalSniped_content" id="GlobalSnipeds">${GlobalSnipedsForView.content}</textarea>
				<input type="hidden" value="<c:out value="${GlobalSnipedsForView.id}"/>" name="GlobalSniped_id" id="GlobalSniped_id">
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
				<button class="buttonKleinSchmal" value="Speichern" style="vertical-align:middle"><span><i class="fa fa-save"></i></span></button>
			</td>
	</form>
	<form action="CopyGlobalSnipedToDocument.secu" method="get">
			<td>
				<select name="documentId">
					<c:forEach items="${DocumentsForView}" var="DocumentsForView">
							<option value="${DocumentsForView.id}">${DocumentsForView.documentname}</option>
					</c:forEach>
				</select>
			</td>
			<td>
				<input type="hidden" value="<c:out value="${GlobalSnipedsForView.id}"/>" name="GlobalSniped_id" id="GlobalSniped_id">
				<input type="hidden" value="<c:out value="${GlobalSnipedsForView.content_type}"/>" name="GlobalSniped_content_type" id="GlobalSniped_content_type">
				<button class="buttonKleinBreit" value="Kopieren" style="vertical-align:middle">Zu Dokument kopieren</button>
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
					<select name="ContribteDocument">
						<c:forEach items="${DocumentsForView}" var="DocumentsForView">
							<option value="${DocumentsForView.id}">${DocumentsForView.documentname}</option>
						</c:forEach>
					</select>
					<select name="ContributeUser">
						<c:forEach items="${Contributors}" var="Contributors">
							<option value="${Contributors.id}">${Contributors.muname}</option>
						</c:forEach>
					</select>
					<button class="buttonKleinBreit" value="Speichern" style="vertical-align:middle">Einladen</button>
				</td>
			</form>
		</tr>
	</table>
	<table>
	<c:forEach items="${SavedContributors}" var="SavedContributors">
		<tr>
			<td>
				<c:forEach items="${DocumentsForView}" var="DocumentsForView">
					<c:forEach items="${Contributors}" var="Contributors">
							<c:if test="${Contributors.id == SavedContributors.contribute_muser_id}">
									<c:if test="${DocumentsForView.id == SavedContributors.document_id}">
										${Contributors.muname} - ${DocumentsForView.documentname}
									</c:if>
							</c:if>
					</c:forEach>
				</c:forEach>
			</td>
			<td>
				<form action="removecontributor.secu" method="get">
					<input type="hidden" value="<c:out value="${SavedContributors.id}"/>" name="contribute_id" id="contribute_id">
					<button class="buttonKleinBreit" value="Entfernen" style="vertical-align:middle">Entfernen</button>
				</form>
			</td>
		</tr>
	</c:forEach>
</table>