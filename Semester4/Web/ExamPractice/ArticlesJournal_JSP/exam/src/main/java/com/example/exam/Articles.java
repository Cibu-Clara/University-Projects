package com.example.exam;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "articles", value = "/articles")
public class Articles extends HttpServlet {
    private String name;
    private String journal = "";
    public void doGet(HttpServlet Request request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        this.name = request.getParameter("name");
        request.getSession().setAttribute("name", name);
        this.journal = request.getParameter("journal");
        request.getSession().setAttribute("journal", journal);
        List<Article> articles = new DB_Manager().getArticlesFromJournal(name, journal);
        request.getSession().setAttribute("articles", articles);
        request.getRequestDispatcher("article.jsp").forward(request, response);
    }

}