<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Exam Practice</title>
</head>
<body>
<form action="userProjects.jsp">
    <label>
        <input type="text" placeholder="User Name" name="name">
    </label>
    <button>User's Projects</button>
</form>

<button onclick="window.location.href = 'all-projects';">Show Projects</button>
<button onclick="window.location.href = 'softwareDevelopers.jsp';">Show Software Developers</button>

</body>
</html>