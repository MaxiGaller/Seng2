<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">

<c:if test="${mode == 'write'}">
<span style="font-size: 50px; font-family: Arial; margin-left: 20px; color:#4169e1;"><b><c:out value="${documentname}"/> bearbeiten</b></span>
</c:if>
<c:if test="${mode == 'read'}">
<span style="font-size: 50px; font-family: Arial; margin-left: 20px; color:#4169e1;"><b><c:out value="${documentname}"/> lesen</b></span>
</c:if>
<c:if test="${mode == 'showlatex'}">
<span style="font-size: 50px; font-family: Arial; margin-left: 20px; color:#4169e1;"><b>LaTeX Export <c:out value="${documentname}"/></b></span>
</c:if>

<c:if test="${mode == 'write'}">
<form action="renamedocument.secu" method="get">
	<input type="hidden" value="<c:out value="${documentId}"/>" name="documentId" id="documentId">
	<input type="hidden" value="<c:out value="${mode}"/>" name="mode">
	<input type="hidden" value="<c:out value="${documentauthor}"/>" name="documentauthor" id="documentauthor">
	<input class="eingabefeld" type="text" name="documentname" id="documentname" value="<c:out value="${documentname}"/>" style="margin-left: 20px;">
	<button class="buttonKlein" type="submit" value="&Auml;ndern" style="vertical-align:middle"><span>&Auml;ndern</span></button>
</form>
<form action="setauthor.secu" method="get">
	<input type="hidden" value="<c:out value="${documentId}"/>" name="documentId" id="documentId">
	<input type="hidden" value="<c:out value="${documentname}"/>" name="documentname" id="documentname">
	<input type="hidden" value="<c:out value="${mode}"/>" name="mode">
	<input class="eingabefeld" type="text" name="documentauthor" id="documentauthor" value="<c:out value="${documentauthor}"/>" style="margin-left: 20px;">
	<button class="buttonKlein" type="submit" value="&Auml;ndern" style="vertical-align:middle"><span>&Auml;ndern</span></button>
</form>
</c:if>

<hr>

<table>
	<c:if test="${mode == 'showlatex'}">
	<tr>
		<td>
			\documentclass{scrartcl}</br>
			</br>
			\usepackage[utf8]{inputenc}</br>
			\usepackage[T1]{fontenc}</br>
			\usepackage{lmodern}</br>
			\usepackage[ngerman]{babel}</br>
			\usepackage{amsmath}</br>
			\title{<c:out value="${documentname}"/>}</br>
			\author{<c:out value="${documentauthor}"/>}</br>
			\begin{document}</br>
			</br>
			\maketitle</br>
			\tableofcontents</br>
			</br>
			\pagebreak</br>
			</br>
			</br>
		</td>
	</tr>
	</c:if>
	<c:forEach items="${SnipedsForView}" var="SnipedsForView">
	<c:if test="${SnipedsForView.editable == 1}">
		<tr>
			<c:if test="${mode == 'write'}">
				<td>
					<form action="editsniped.secu" method="get">
						<textarea rows="4"; cols="50"; name="snipedContent" id="snipedContent"><c:out value="${SnipedsForView.content}"/></textarea>
						<input type="hidden" value="<c:out value="${SnipedsForView.id}"/>" name="snipedId" id="snipedId">
						<input type="hidden" value="<c:out value="${SnipedsForView.document_id}"/>" name="documentId" id="documentId">
						<input type="hidden" value="<c:out value="${mode}"/>" name="mode">
						<input type="hidden" value="<c:out value="${documentauthor}"/>" name="documentauthor" id="documentauthor">
						<input type="hidden" value="<c:out value="${documentname}"/>" name="documentname" id="documentname">
				</td>
			</c:if>
			<c:if test="${mode == 'read'}">
				<td>
					<c:forEach items="${TypesForView}" var="TypesForView">
						<c:if test="${TypesForView.id == SnipedsForView.content_type}">
							${TypesForView.html_opening_tag}
						</c:if>
					</c:forEach>
					<c:out value="${SnipedsForView.content}"/>
					<c:forEach items="${TypesForView}" var="TypesForView">
						<c:if test="${TypesForView.id == SnipedsForView.content_type}">
							${TypesForView.html_closeing_tag}
						</c:if>
					</c:forEach>
				</td>
			</c:if>
			<c:if test="${mode == 'showlatex'}">
				<td>				
					<c:forEach items="${TypesForView}" var="TypesForView">
						<c:if test="${TypesForView.id == SnipedsForView.content_type}">
							${TypesForView.type_opening_tag}
						</c:if>
					</c:forEach>
					<c:out value="${SnipedsForView.content}"/>
					<c:forEach items="${TypesForView}" var="TypesForView">
						<c:if test="${TypesForView.id == SnipedsForView.content_type}">
							${TypesForView.type_closeing_tag}
						</c:if>
					</c:forEach>
				</td>
			</c:if>
			<td>
				<c:if test="${mode == 'write'}">
				<form>
					<select name="content_type">
						<c:forEach items="${TypesForView}" var="TypesForView">
							<c:if test="${TypesForView.id == SnipedsForView.content_type}">
								<option value="<c:out value="${TypesForView.id}"/>" selected>${TypesForView.type}</option>
							</c:if>
							<c:if test="${TypesForView.id != SnipedsForView.content_type}">
								<option value="${TypesForView.id}">${TypesForView.type}</option>
							</c:if>
						</c:forEach>
					</select>
					<button class="buttonKleinSchmal" value="Speichern" style="vertical-align:middle"><span><i class="fa fa-save"></i></span></button>
				</form>
				</c:if>
				<c:if test="${mode == 'read'}">
					<c:forEach items="${TypesForView}" var="TypesForView">
						<c:if test="${TypesForView.id == SnipedsForView.content_type}">
							${TypesForView.type}
						</c:if>
					</c:forEach>
				</c:if>
			</td>
			</c:if>
			<c:if test="${SnipedsForView.editable == 0}">	
				<c:if test="${mode == 'write'}">
					<c:forEach items="${GlobalSnipedsForView}" var="GlobalSnipedsForView">
						<c:if test="${SnipedsForView.global_Sniped_id == GlobalSnipedsForView.id}">
							<td>
								${GlobalSnipedsForView.content}
							</td>
						</c:if>
					</c:forEach>
				</c:if>
				<c:if test="${mode != 'write'}">
					<c:if test="${SnipedsForView.editable == 0}">	
						<c:forEach items="${GlobalSnipedsForView}" var="GlobalSnipedsForView">
							<c:if test="${SnipedsForView.global_Sniped_id == GlobalSnipedsForView.id}">
								<td>
									<c:forEach items="${TypesForView}" var="TypesForView">
										<c:if test="${TypesForView.id == GlobalSnipedsForView.content_type}">
											<c:if test="${mode == 'showlatex'}">
												${TypesForView.type_opening_tag}
											</c:if>
											<c:if test="${mode == 'read'}">
												${TypesForView.html_opening_tag}
											</c:if>
										</c:if>
									</c:forEach>
									${GlobalSnipedsForView.content}
									<c:forEach items="${TypesForView}" var="TypesForView">
										<c:if test="${TypesForView.id == GlobalSnipedsForView.content_type}">
											<c:if test="${mode == 'showlatex'}">
												${TypesForView.type_opening_tag}
											</c:if>
											<c:if test="${mode == 'read'}">
												${TypesForView.html_closeing_tag}
											</c:if>
										</c:if>
									</c:forEach>
								</td>
							</c:if>
						</c:forEach>
					</c:if>
				</c:if>
			</c:if>
			<c:if test="${mode == 'write'}">
			<td>
				<form action="editSnipedMove.secu" method="post">
					<input type="hidden" value="up" name="type">
					<input type="hidden" value="<c:out value="${SnipedsForView.document_id}"/>" name="documentId">
					<input type="hidden" value="<c:out value="${mode}"/>" name="mode">
					<input type="hidden" value="<c:out value="${SnipedsForView.id}"/>" name="snipedId">
					<input type="hidden" value="<c:out value="${SnipedsForView.ordinal}"/>" name="ordinal">
					<input type="hidden" value="<c:out value="${documentname}"/>" name="documentname">
					<input type="hidden" value="<c:out value="${documentauthor}"/>" name="documentauthor" id="documentauthor">
					<button class="buttonKleinSchmal" value="up" style="vertical-align:middle"><span><i class="fa fa-arrow-up"></i></span></button>
				</form>
				<form action="editSnipedMove.secu" method="post">
					<input type="hidden" value="down" name="type">
					<input type="hidden" value="<c:out value="${SnipedsForView.document_id}"/>" name="documentId">
					<input type="hidden" value="<c:out value="${mode}"/>" name="mode">
					<input type="hidden" value="<c:out value="${SnipedsForView.id}"/>" name="snipedId">
					<input type="hidden" value="<c:out value="${SnipedsForView.ordinal}"/>" name="ordinal">
					<input type="hidden" value="<c:out value="${documentname}"/>" name="documentname">
					<input type="hidden" value="<c:out value="${documentauthor}"/>" name="documentauthor" id="documentauthor">
					<button class="buttonKleinSchmal" value="down" style="vertical-align:middle"><span><i class="fa fa-arrow-down"></i></span></button>
				</form>
			</td>
			<td>
				<form action="deleteSniped.secu" method="get">
					<input type="hidden" value="<c:out value="${documentname}"/>" name="documentname" id="documentname">
					<input type="hidden" value="<c:out value="${documentId}"/>" name="documentId" id="documentId">
					<input type="hidden" value="<c:out value="${mode}"/>" name="mode" id="mode">
					<input type="hidden" value="<c:out value="${documentauthor}"/>" name="documentauthor" id="documentauthor">
					<input type="hidden" value="<c:out value="${SnipedsForView.id}"/>" name="snipedId">
					<button class="buttonKleinSchmal" value="Papierkorb" style="vertical-align:middle"><span><i class="fa fa-trash w3-large"></i></span></button>
				</form>
			</td>
			</c:if>
		</tr>
		<c:if test="${mode != 'showlatex'}">
			<tr>
				<td>
					<hr>
				</td>
			</tr>
		</c:if>
	</c:forEach>
	<c:if test="${mode == 'showlatex'}">
	<tr>
		<td>
			\end{document}</br>
		</td>
	</tr>
	<tr>
		<td>
			<hr>
		</td>
	</tr>
	<tr>
		<td>
			<a href="https://www.overleaf.com/docs?rich_text=true&template=overleaf" target="_blank">Online LaTeX Compiler</a>
		</td>
	</tr>
	</c:if>
</table>

<hr>
<c:if test="${mode == 'write'}">
<span style="margin-left: 20px; font-size: 20px;"><b>Neues Element</b></span>
<form action="newsniped.secu" method="get">
	<table>
		<tr>
			<td>
				<br>
				<textarea rows="4" cols="50" name="snipedContent" id="snipedContent"></textarea>
				<input type="hidden" value="<c:out value="${documentname}"/>" name="documentname" id="documentname">
				<input type="hidden" value="<c:out value="${documentId}"/>" name="documentId" id="documentId">
				<input type="hidden" value="<c:out value="${mode}"/>" name="mode">
				<input type="hidden" value="<c:out value="${documentauthor}"/>" name="documentauthor" id="documentauthor">
				<br>
				<br>
				<select name="content_type">
					<c:forEach items="${TypesForView}" var="TypesForView">
						<option value="<c:out value="${TypesForView.id}"/>">${TypesForView.type}</option>
					</c:forEach>
				</select>
				<button class="buttonKlein" value="Neu Anlegen" style="vertical-align:middle"><span>Neu <i class="fa fa-save"></i></span></button>
			</td>
		</tr>
	</table>
</form>
</c:if>