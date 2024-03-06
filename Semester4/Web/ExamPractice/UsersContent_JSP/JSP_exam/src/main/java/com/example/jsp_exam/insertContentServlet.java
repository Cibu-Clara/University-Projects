package com.example.jsp_exam;

import Domain.Content;
import Domain.Db_manager;
import Domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;

@WebServlet("/insert-content")
public class insertContentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String url = request.getParameter("url");

        String user = null;
        String sid=request.getParameter("id");
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("user")) {
                user = cookie.getValue();
                break;
            }
        }

        Db_manager db = new Db_manager();
        User userr = db.getUserFromName(user);
        int user_id = userr.getId();

        String date = String.valueOf(java.time.LocalDate.now());

        Content content = new Content(date, title, description, url, user_id);
        db.insertContent(content);
        response.sendRedirect("content-display");
    }
}