<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Exam Practice</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="script.js"></script>
<style>
    table {
        border-collapse: collapse;
        max-width: 100%;
    }
    th, td {
        border: 3px double black;
        text-align: left;
    }
</style>
<body>
<%
    String skill = request.getParameter("skill");
    if (skill == null || skill.isEmpty()) {
%>
        <p>Error: Skill parameter is missing or empty.</p>
<%
    } else {
%>
<table id="developersTable"></table>
<%
    }
%>
</body>
</html>
