<%@ page import="com.example.exam.Article" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="articles">
    <label>
        <input id="name" type="text" name="name" value="<%out.println(request.getSession().getAttribute("name"));%>"
               readonly>
    </label>
    <label>
        <input id="journal" type="text" placeholder="journal" name="journal">
    </label>
    <button>View</button>
</form>
<p>
    <%
        List<Article> articles = (List<Article>) request.getSession().getAttribute("articles");
        for (Article article : articles) {
            out.println(article.toString());
        }
    %>
</p>
</body>
</html>
