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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "DeleteContentServlet", value = "/delete-content-servlet")
public class DeleteContentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = null;
        String sid=request.getParameter("id");
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("user")) {
                username = cookie.getValue();
                break;
            }
        }

        int id=Integer.parseInt(sid);

        Db_manager db = new Db_manager();
        User user = db.getUserFromName(username);
        Content content = db.getContentFromId(id);

        if (content.getUser_id() == user.getId()){
            db.removeContent(content.getId());
            response.sendRedirect("content-display");
        }



    }
}
