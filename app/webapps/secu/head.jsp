<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>A real safe and high performant portal</title>

    <link rel="stylesheet" href="stylesheets/master.css" type="text/css">

    <script src="js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="js/popup.js" type="text/javascript"></script>
    <script type="text/javascript" src="js/jqpopup.js"></script>


</head>

<body>

<!-- Links für das Menü auf der linken Seite -->

<ul id="navigation">
    <img src="img/logo.png" width="100%"/>
    <c:out value="${message}"/>
    <li><a href="/secu/">Start / Main</a></li>
    <li><a href="search.secu">search the web</a></li>
    <li><a href="picturegallery.secu">watch beautiful pictures</a></li>
    <li><a href="login.secu">login into area42 </a></li>
    <li><a href="register.secu">Register</a></li>
    <li><a href="intern.secu">Userbereich</a></li>
    <li><a href="mailing.secu">mailinglist</a></li>
    <li><a href="newsletter.secu">newsletter</a></li>
    <li><a href="help.secu">FAQ / Helptopics</a> </li>
</ul>

<div id="Inhalt">
