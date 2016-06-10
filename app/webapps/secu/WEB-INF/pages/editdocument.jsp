<%--@elvariable id="userDomain" type="edu.hm.muse.domain.User"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1><c:out value="${documentname}"/> - Bearbeiten</h1>

Dokumentnamen &auml;ndern in
<form action="renamedocument.secu" method="get">
	<input type="hidden" value="<c:out value="${documentId}"/>" name="documentId" id="documentId">
	<input type="text" value="<c:out value="${documentname}"/>" name="documentname" id="documentname">
	<input type="submit" value="&Auml;ndern">
</form>

<table>
	<c:forEach items="${SnipedsForView}" var="SnipedsForView">
		<tr>
			<td>${SnipedsForView.type}</td>
		</tr>
	    <tr>      
	        <td>
				<form action="editsniped.secu" method="get">
					<textarea rows="4" cols="50" name="snipedContent" id="snipedContent">${SnipedsForView.content}</textarea>
					<input type="hidden" value="<c:out value="${SnipedsForView.id}"/>" name="snipedId" id="snipedId">
					<input type="hidden" value="<c:out value="${SnipedsForView.document_id}"/>" name="documentId" id="documentId">
					<input type="hidden" value="<c:out value="${documentname}"/>" name="documentname" id="documentname">
			</td>
	        <td>
				    <input type="submit" value="Speichern">
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
				</form>
			</td>
            <td>

                <form action="editSnipedMove.secu" method="post">
                    <input type="hidden" value="up" name="type">
                    <input type="hidden" value="<c:out value="${SnipedsForView.document_id}"/>" name="documentId">
                    <input type="hidden" value="<c:out value="${SnipedsForView.id}"/>" name="snipedId">
                    <input type="hidden" value="<c:out value="${SnipedsForView.ordinal}"/>" name="ordinal">
                    <input type="hidden" value="<c:out value="${documentname}"/>" name="documentname">
                    
                    <button class="buttonKlein" value="up" style="vertical-align:middle"><span>Up</span></button>
                </form>
                <form action="editSnipedMove.secu" method="post">
                    <input type="hidden" value="down" name="type">
                    <input type="hidden" value="<c:out value="${SnipedsForView.document_id}"/>" name="documentId">
                    <input type="hidden" value="<c:out value="${SnipedsForView.id}"/>" name="snipedId">
                    <input type="hidden" value="<c:out value="${SnipedsForView.ordinal}"/>" name="ordinal">
                    <input type="hidden" value="<c:out value="${documentname}"/>" name="documentname">
                    <button class="buttonKlein" value="down" style="vertical-align:middle"><span>Down</span></button>
                </form>
            </td>
	    </tr>
	</c:forEach>
</table>

<hr>
							
<form action="newsniped.secu" method="get">
	<table>
		<tr>  
			<td>
				<textarea rows="4" cols="50" name="snipedContent" id="snipedContent"></textarea>
				<input type="hidden" value="${documentname}" name="documentname" id="documentname">
				<input type="hidden" value="${documentId}" name="documentId" id="documentId">
			    <input type="submit" value="Neu Anlegen">
			    <select name="content_type">	
				    <c:forEach items="${TypesForView}" var="TypesForView">
						<option value="${TypesForView.id}">${TypesForView.type}</option>
					</c:forEach>	
				</select>
			</td>
		</tr>
	</table>
</form>

<jsp:include page="../help/acc_help.jsp"/>
<jsp:include page="../../foot.jsp"/>