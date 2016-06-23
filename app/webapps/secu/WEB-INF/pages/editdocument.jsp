<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>

<c:if test="${mode == 'write'}">
    <div class="m1">LaTeX bearbeiten</div>
    <br>
    <div class="m2">Dokument: <b><c:out value="${documentname}"/></b></div>
    <div class="m2">Autor: <b><c:out value="${documentauthor}"/></b></div>
    <br><br>
</c:if>
<c:if test="${mode == 'read'}">
    <div class="m1">LaTeX lesen</div>
    <br>
    <div class="m2">Dokument: <b><c:out value="${documentname}"/></b></div><br>
</c:if>
<c:if test="${mode == 'showlatex'}">
    <div class="m1">LaTeX Exportieren</div>
    <br>
    <div class="m2">Dokument: <b><c:out value="${documentname}"/></b></div><br>
</c:if>

<c:if test="${mode == 'write'}">
    <form action="renamedocument.secu" method="get">
        <input type="hidden" value="<c:out value="${documentId}"/>" name="documentId">
        <input type="hidden" value="<c:out value="${mode}"/>" name="mode">
        <input type="hidden" value="<c:out value="${documentauthor}"/>" name="documentauthor">
        <input class="eingabefeld" type="text" name="documentname" placeholder="neuer Dokumentenname"
               style="margin-left: 20px;">
        <button class="button breite3 schrift3 hoehe3 grau" value="change" style="margin-left: 20px;">
            <span>&Auml;ndern <i class="fa fa-edit"></i></span></button>
    </form>
    <form action="setauthor.secu" method="get">
        <input type="hidden" value="<c:out value="${documentId}"/>" name="documentId">
        <input type="hidden" value="<c:out value="${documentname}"/>" name="documentname">
        <input type="hidden" value="<c:out value="${mode}"/>" name="mode">
        <input class="eingabefeld" type="text" name="documentauthor" placeholder="neuer Autorenname"
               style="margin-left: 20px;">
        <button class="button breite3 schrift3 hoehe3 grau" value="change" style="margin-left: 20px;">
            <span>&Auml;ndern <i class="fa fa-edit"></i></span></button>
    </form>
</c:if>

<br><hr>

<table>
    <c:if test="${mode == 'showlatex'}">
        <tr>
            <td>
                <div style="margin-left: 20px;">
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
                </div>
            </td>
        </tr>
    </c:if>
    <c:forEach items="${SnipedsForView}" var="SnipedsForView">
        <c:if test="${SnipedsForView.editable == 1}">
            <tr>
            <c:if test="${mode == 'write'}">
                <td>
                    <form action="editsniped.secu" method="get">
                        <span style="margin-left: 20px;"><textarea rows="4" ; cols="50" ; name="snipedContent"><c:out
                                value="${SnipedsForView.content}"/></textarea></span>
                        <input type="hidden" value="<c:out value="${SnipedsForView.id}"/>" name="snipedId">
                        <input type="hidden" value="<c:out value="${SnipedsForView.document_id}"/>" name="documentId">
                        <input type="hidden" value="<c:out value="${mode}"/>" name="mode">
                        <input type="hidden" value="<c:out value="${documentauthor}"/>" name="documentauthor">
                        <input type="hidden" value="<c:out value="${documentname}"/>" name="documentname">
                    </form>
                </td>
            </c:if>
            <c:if test="${mode == 'read'}">
                <td>
                    <div style="margin-left: 20px;"><c:forEach items="${TypesForView}" var="TypesForView">
                        <c:if test="${TypesForView.id == SnipedsForView.content_type}">
                            ${TypesForView.html_opening_tag}
                        </c:if>
                    </c:forEach>
                    <c:out value="${SnipedsForView.content}"/>
                    <c:forEach items="${TypesForView}" var="TypesForView">
                        <c:if test="${TypesForView.id == SnipedsForView.content_type}">
                            ${TypesForView.html_closeing_tag}
                        </c:if>
                    </c:forEach></div>
                </td>
            </c:if>
            <c:if test="${mode == 'showlatex'}">
                <td>
                    <div style="margin-left: 20px;"><c:forEach items="${TypesForView}" var="TypesForView">
                        <c:if test="${TypesForView.id == SnipedsForView.content_type}">
                            ${TypesForView.type_opening_tag}
                        </c:if>
                    </c:forEach>
                    <c:out value="${SnipedsForView.content}"/>
                    <c:forEach items="${TypesForView}" var="TypesForView">
                        <c:if test="${TypesForView.id == SnipedsForView.content_type}">
                            ${TypesForView.type_closeing_tag}
                        </c:if>
                    </c:forEach></div>
                </td>
            </c:if>
            <td>
                <c:if test="${mode == 'write'}">
                    <form>
                        <select name="content_type">
                            <c:forEach items="${TypesForView}" var="TypesForView">
                                <c:if test="${TypesForView.id == SnipedsForView.content_type}">
                                    <option value="<c:out value="${TypesForView.id}"/>"
                                            selected>${TypesForView.type}</option>
                                </c:if>
                                <c:if test="${TypesForView.id != SnipedsForView.content_type}">
                                    <option value="${TypesForView.id}">${TypesForView.type}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <button class="button breite4 schrift3 hoehe4 hellBlau" value="save" style="margin-left: 20px;">
                            <span><i class="fa fa-save"></i></span></button>
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
                <c:if test="${SnipedsForView.ordinal != 0}">
                    <form action="editSnipedMove.secu" method="post">
                        <input type="hidden" value="up" name="type">
                        <input type="hidden" value="<c:out value="${SnipedsForView.document_id}"/>" name="documentId">
                        <input type="hidden" value="<c:out value="${mode}"/>" name="mode">
                        <input type="hidden" value="<c:out value="${SnipedsForView.id}"/>" name="snipedId">
                        <input type="hidden" value="<c:out value="${SnipedsForView.ordinal}"/>" name="ordinal">
                        <input type="hidden" value="<c:out value="${documentname}"/>" name="documentname">
                        <input type="hidden" value="<c:out value="${documentauthor}"/>" name="documentauthor">
                        <button class="button breite4 schrift3 hoehe4 grau" value="up" style="margin-left: 20px;"><span><i
                                class="fa fa-arrow-up"></i></span></button>
                    </form>
                </c:if>
            </td>
            <td>
                <c:if test="${SnipedsForView.ordinal != maxOrdinal}">
                    <form action="editSnipedMove.secu" method="post">
                        <input type="hidden" value="down" name="type">
                        <input type="hidden" value="<c:out value="${SnipedsForView.document_id}"/>" name="documentId">
                        <input type="hidden" value="<c:out value="${mode}"/>" name="mode">
                        <input type="hidden" value="<c:out value="${SnipedsForView.id}"/>" name="snipedId">
                        <input type="hidden" value="<c:out value="${SnipedsForView.ordinal}"/>" name="ordinal">
                        <input type="hidden" value="<c:out value="${documentname}"/>" name="documentname">
                        <input type="hidden" value="<c:out value="${documentauthor}"/>" name="documentauthor">
                        <button class="button breite4 schrift3 hoehe4 grau" value="down" style="margin-left: 20px;"><span><i
                                class="fa fa-arrow-down"></i></span></button>
                    </form>
                </c:if>
            </td>
            <td>
                <form action="deleteSniped.secu" method="get">
                    <input type="hidden" value="<c:out value="${documentname}"/>" name="documentname">
                    <input type="hidden" value="<c:out value="${documentId}"/>" name="documentId">
                    <input type="hidden" value="<c:out value="${mode}"/>" name="mode">
                    <input type="hidden" value="<c:out value="${documentauthor}"/>" name="documentauthor">
                    <input type="hidden" value="<c:out value="${SnipedsForView.id}"/>" name="snipedId">
                    <button class="button breite4 schrift3 hoehe4 hellRot" value="Papierkorb"
                            style="margin-left: 20px;"><span><i class="fa fa-trash w3-large"></i></span></button>
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
                <span style="margin-left: 20px;">\end{document}</span></br>
            </td>
        </tr>
        <tr>
            <td>
                <hr>
            </td>
        </tr>
        <tr>
            <td>
                <br><span class="m4">Link zur Online LaTeX-Erstellung:</span>
                <a href="https://www.overleaf.com/docs?rich_text=true&template=overleaf" target="_blank"><span
                        class="m3" style="font-style:italic; color: blue">Online LaTeX Compiler</span></a>
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
                    <span style="margin-left: 20px;"><textarea rows="4" cols="50" name="snipedContent"></textarea></span>
                    <input type="hidden" value="<c:out value="${documentname}"/>" name="documentname">
                    <input type="hidden" value="<c:out value="${documentId}"/>" name="documentId">
                    <input type="hidden" value="<c:out value="${mode}"/>" name="mode">
                    <input type="hidden" value="<c:out value="${documentauthor}"/>" name="documentauthor">
                    <br>
                    <br>
				<span style="margin-left: 20px;"><select name="content_type">
					<c:forEach items="${TypesForView}" var="TypesForView">
                        <option value="<c:out value="${TypesForView.id}"/>">${TypesForView.type}</option>
                    </c:forEach>
				</select></span>
                    <button class="button breite3 schrift3 hoehe3 hellBlau" value="Neu Anlegen"
                            style="margin-left: 20px;"><span>Neu <i class="fa fa-save"></i></span></button>
                </td>
            </tr>
        </table>
    </form>
</c:if>