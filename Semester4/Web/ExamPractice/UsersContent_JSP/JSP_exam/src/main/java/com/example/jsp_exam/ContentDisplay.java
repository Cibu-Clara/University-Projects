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
import java.util.List;

@WebServlet(name = "contentDisplay", value = "/content-display")
public class ContentDisplay extends HttpServlet {
    public ContentDisplay(){super();}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>Content List</h1>");

        List<Content> content_list = new Db_manager().getAllContents();


        out.print("<table border='1' width='100%'");
        out.print("<tr><th>Id</th><th>Date</th><th>title</th><th>Description</th><th>Url</th><th>User Id</th><th>Edit</th><th>Delete</th></tr>");
        for(Content c: content_list){
            out.print("<tr><td>"+c.getId()+"</td><td>"+c.getDate()+"</td><td>"+c.getTitle()+"</td>  <td>"+c.getDescription()+"</td><td>"+c.getUrl()+"</td><td>"+c.getUser_id()+"</td><td><a href='update-content?id="+c.getId()+"'>edit</a></td>   <td><a href='delete-content-servlet?id="+c.getId()+"'>delete</a></td></tr>");
        }
        out.print("</table>");

        out.close();
    }
}
