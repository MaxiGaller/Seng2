<%--@elvariable id="userDomain" type="edu.hm.muse.domain.User"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1><c:out value="${documentname}"/> - Bearbeiten</h1>

Usergruppe Anlegen &auml;ndern in
<form action="renamedocument.secu" method="get">
	<input type="hidden" value="<c:out value="${documentId}"/>" name="documentId" id="documentId">
	<input type="text" value="<c:out value="${documentname}"/>" name="documentname" id="documentname">
	
    <c:forEach items="${UserIDs}" var="UserIDs">
		<option value="${UserIDs.id}">${UserIDs.muname}</option>
	</c:forEach>
	
	</c:forEach>
	
	<input type="submit" value="&Auml;ndern">
</form>

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