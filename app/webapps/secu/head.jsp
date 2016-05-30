<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Galler du stinkst</title>

    <link rel="stylesheet" href="stylesheets/master.css" type="text/css">

    <script src="js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="js/popup.js" type="text/javascript"></script>
    <script type="text/javascript" src="js/jqpopup.js"></script>


</head>

<body>

<!-- Links für das Menü auf der linken Seite -->

<ul id="navigation">
    <a href="http://localhost:8081/secu/"><img src="img/HMLogo.png" alt="Bild" width="80%" /></a>
    <br>
    <% if ((session.getAttribute("login") == null) ) { %>
    <li><a href="login.secu">Login</a></li>
    <li><a href="register.secu">Registrieren</a> </li>
    <% } %>

    <% if ((session.getAttribute("login") != null) ) { %>
    <li><a href="intern.secu">Benutzerkonto</a></li>
    <li><a href="projects.secu">LaTeX Editor</a></li>
    <li><a href="logout.secu">Logout</a></li>
    <% } %>
</ul>

<div id="Inhalt">
    </div>