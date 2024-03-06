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

@WebServlet("/update-content-2")
public class updateContentServlet2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

        String sid=request.getParameter("id");
        int id = Integer.parseInt(sid);
        String date = request.getParameter("date");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String url = request.getParameter("url");
        String user_idS = request.getParameter("user_id");
        int user_id = Integer.parseInt(user_idS);

        Content content = new Content(id, date, title, description, url, user_id);

        int status = new Db_manager().updateContent(content);

        if(status > 0){
//            out.println("done");
            response.sendRedirect("content-display");
        }else{
            out.println("Sorry! unable to update record");
        }

        out.close();
    }
}
