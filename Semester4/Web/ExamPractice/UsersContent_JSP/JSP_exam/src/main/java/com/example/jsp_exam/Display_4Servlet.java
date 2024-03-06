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
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

@WebServlet(name = "Display4", value = "/display-4")
public class Display_4Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        Db_manager db = new Db_manager();
        List<Content> contentList = db.getAllContents();
        int startIndex = Math.max(contentList.size() - 4, 0);
        List<Content> last4 = contentList.subList(startIndex, contentList.size() );

        Gson gson = new Gson();
        String json = gson.toJson(last4);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}