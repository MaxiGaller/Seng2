<%--@elvariable id="userDomain" type="edu.hm.muse.domain.User"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">

<span style="font-size: 50px; font-family: Arial; margin-left: 20px; color:#4169e1;"><b>Dokument bearbeiten</b></span>
<br>
<span style="margin-left: 20px; font-size: 20px;">Dokumentenname: <b><c:out value="${documentname}"/></b></span>
<br><br><br>
<span style="margin-left: 20px; font-size: 15px;">Dokumentnamen &auml;ndern in:</span>
<br>
<form action="renamedocument.secu" method="get">
	<input type="hidden" value="<c:out value="${documentId}"/>" name="documentId" id="documentId">
	<input class="eingabefeld" type="text" name="documentname" id="documentname" placeholder="neuer Name" style="margin-left: 20px;">
	<button class="buttonKlein" type="submit" value="&Auml;ndern" style="vertical-align:middle"><span>&Auml;ndern</span></button>
	<br><br><br>
</form>

<table>
	<c:forEach items="${SnipedsForView}" var="SnipedsForView">
		<tr>
			<td>${SnipedsForView.type}</td>
		</tr>
	    <tr>      
	        <td>
				<form action="editsniped.secu" method="get">
					<textarea rows="4"; cols="50"; name="snipedContent"; id="snipedContent";>${SnipedsForView.content}</textarea>
					<input type="hidden" value="<c:out value="${SnipedsForView.id}"/>" name="snipedId" id="snipedId">
					<input type="hidden" value="<c:out value="${SnipedsForView.document_id}"/>" name="documentId" id="documentId">
					<input type="hidden" value="<c:out value="${documentname}"/>" name="documentname" id="documentname">

			</td>

	        <td>
				<form>
					<select name="content_type">
						<c:forEach items="${TypesForView}" var="TypesForView">
							<c:if test="${TypesForView.id == SnipedsForView.content_type}">
								<option value="${TypesForView.id}" selected>${TypesForView.type}</option>
							</c:if>
							<c:if test="${TypesForView.id != SnipedsForView.content_type}">
								<option value="${TypesForView.id}">${TypesForView.type}</option>
							</c:if>
						</c:forEach>
					</select>
					<br><button class="buttonKleinSchmal" value="Speichern" style="vertical-align:middle"><span><i class="fa fa-save"></i></span></button>
				</form>
			</td>

            <td>

                <form action="editSnipedMove.secu" method="post">
                    <input type="hidden" value="up" name="type">
                    <input type="hidden" value="<c:out value="${SnipedsForView.document_id}"/>" name="documentId">
                    <input type="hidden" value="<c:out value="${SnipedsForView.id}"/>" name="snipedId">
                    <input type="hidden" value="<c:out value="${SnipedsForView.ordinal}"/>" name="ordinal">
                    <input type="hidden" value="<c:out value="${documentname}"/>" name="documentname">
                    <button class="buttonKleinSchmal" value="up" style="vertical-align:middle"><span><i class="fa fa-arrow-up"></i></span></button>
				</form>
                <form action="editSnipedMove.secu" method="post">
                    <input type="hidden" value="down" name="type">
                    <input type="hidden" value="<c:out value="${SnipedsForView.document_id}"/>" name="documentId">
                    <input type="hidden" value="<c:out value="${SnipedsForView.id}"/>" name="snipedId">
                    <input type="hidden" value="<c:out value="${SnipedsForView.ordinal}"/>" name="ordinal">
                    <input type="hidden" value="<c:out value="${documentname}"/>" name="documentname">
                    <button class="buttonKleinSchmal" value="down" style="vertical-align:middle"><span><i class="fa fa-arrow-down"></i></span></button>
                </form>
            </td>
	    </tr>
	</c:forEach>
</table>

<hr>
<span style="margin-left: 20px; font-size: 20px;"><b>Neues Element</b></span>
<form action="newsniped.secu" method="get">
	<table>
		<tr>  
			<td>
				<br>
				<textarea rows="4" cols="50" name="snipedContent" id="snipedContent"></textarea>
				<input type="hidden" value="${documentname}" name="documentname" id="documentname">
				<input type="hidden" value="${documentId}" name="documentId" id="documentId">

				<br><br>
				<select name="content_type">
				    <c:forEach items="${TypesForView}" var="TypesForView">
						<option value="${TypesForView.id}">${TypesForView.type}</option>
					</c:forEach>	
				</select>
				<button class="buttonKlein" value="Neu Anlegen" style="vertical-align:middle"><span>Neu <i class="fa fa-save"></i></span></button>

			</td>
		</tr>
	</table>
</form>