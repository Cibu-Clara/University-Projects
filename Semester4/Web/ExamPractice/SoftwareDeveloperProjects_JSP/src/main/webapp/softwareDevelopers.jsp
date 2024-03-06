<%@ page import="utils.DBConnectionManager" %>
<%@ page import="java.util.List" %>
<%@ page import="models.SoftwareDeveloper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Exam Practice</title>
</head>
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
<h1>Software Developers</h1>
<body>
<table>
    <tr><th>Id</th><th>Name</th><th>Age</th><th>Skills</th></tr>
<%
    DBConnectionManager db = new DBConnectionManager();

    List<SoftwareDeveloper> developers = db.getAllDevelopers();
    for(SoftwareDeveloper sd : developers){
            out.print("<tr><td>"+sd.getId()+"</td><td>"+sd.getName()+"</td><td>"+sd.getAge()+"</td><td>"+sd.getSkills()+"</td></tr>");
    }
%>
</table>
<br>
<form action="filterDevelopers.jsp">
    <label>
        <input type="text" id="skillFilter" placeholder="Enter a skill" name="skill">
    </label>
    <button>Filter by skill</button>
</form>
</body>
</html>
