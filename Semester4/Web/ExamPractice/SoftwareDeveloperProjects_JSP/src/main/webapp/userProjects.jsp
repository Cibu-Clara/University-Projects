<%@ page import="java.util.List" %>
<%@ page import="utils.DBConnectionManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Exam Practice</title>
</head>
<body>
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
<%
    String name = request.getParameter("name");
    if (name == null || name.isEmpty()) {
%>
    <p>Error: Name parameter is missing or empty.</p>
<%
    } else {
        DBConnectionManager db = new DBConnectionManager();
        List<String> projects = db.getUserProjects(name);
%>
<table>
    <tr>
        <th><%=name%>'s Projects</th>
    </tr>
<%
    for (String p : projects) {
        out.println("<tr><td>" + p + "</td></tr>");}
%>
</table>
<%
    }
%>

</body>
</html>
