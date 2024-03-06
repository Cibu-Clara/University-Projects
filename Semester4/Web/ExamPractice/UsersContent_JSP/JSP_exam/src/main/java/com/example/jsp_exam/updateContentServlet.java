package com.example.jsp_exam;

import Domain.Content;
import Domain.Db_manager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/update-content")
public class updateContentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        Db_manager db = new Db_manager();


        PrintWriter out=response.getWriter();

        out.println("<h1>Update Content</h1>");
        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);
        Content content = db.getContentFromId(id);

        out.print("<form action='update-content-2' method='post'>");
        out.print("<table>");

        out.print("<tr><td></td><td><input type='hidden' name='id' value='"+content.getId()+"'/></td></tr>");
        out.print("<tr><td>Date:</td><td><input type='text' name='date' value='"+content.getDate()+"'/></td></tr>");
        out.print("<tr><td>Title:</td><td><input type='text' name='title' value='"+content.getTitle()+"'/></td></tr>");
        out.print("<tr><td>Description:</td><td><input type='text' name='description' value='"+content.getDescription()+"'/></td></tr>");
        out.print("<tr><td>Url:</td><td><input type='text' name='url' value='"+content.getUrl()+"'/></td></tr>");
        out.print("<tr><td>User Id:</td><td><input type='text' name='user_id' value='"+content.getUser_id()+"'/></td></tr>");

        out.print("</td></tr>");
        out.print("<tr><td colspan='2'><input type='submit' value='Edit & Save '/></td></tr>");
        out.print("</table>");
        out.print("</form>");

        out.close();

    }
}