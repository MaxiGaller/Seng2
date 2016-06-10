<!DOCTYPE html>
<html>
<head>
    <title>Max Haubelt ist ein geiler Typ</title>
    <link rel="stylesheet" href="stylesheets/master.css" type="text/css">
    <script src="js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="js/popup.js" type="text/javascript"></script>
    <script type="text/javascript" src="js/jqpopup.js"></script>
    <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
    <style>
        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
            background-color: #333;
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 10;
            font-size: 18px;
        }

        li {
            float: left;
        }

        li a {
            display: block;
            color: white;
            text-align: center;
            padding: 18px 30px;
            text-decoration: none;
        }

        li a:hover:not(.active) {
            background-color: #111;
        }

        .active {
            background-color: #4CAF50;
        }
    </style>
</head>

<body>

<ul>
    <%--<img src="img/iDontKnow.gif" width="300px" align="left">--%>
    <li><a class="active" href="http://localhost:8081/secu/"><i class="fa fa-home"></i> Home</a></li>
    <%--<li><a class="active" href="http://localhost:8081/secu/">Home</a></li>--%>

    <% if ((session.getAttribute("login") == null) ) { %>
    <li><a href="login.secu" class="menubutton" >Login</a></li>
    <li><a href="register.secu" class="menubutton">Registrieren</a> </li>
    <% } %>

    <% if ((session.getAttribute("login") != null) ) { %>
    <li><a href="intern.secu" class="menubutton">Benutzerkonto</a></li>
    <li><a href="projects.secu" class="menubutton">LaTeX Editor</a></li>
    <li><a href="logout.secu" class="menubutton">Logout</a></li>
    <% } %>
</ul>
<br><br><br>
</body>
</html>