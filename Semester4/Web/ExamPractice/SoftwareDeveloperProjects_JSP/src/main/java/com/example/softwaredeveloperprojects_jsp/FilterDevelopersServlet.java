package com.example.softwaredeveloperprojects_jsp;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.SoftwareDeveloper;
import utils.DBConnectionManager;
import java.io.IOException;
import java.util.List;

@WebServlet("/filter-developers")
public class FilterDevelopersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnectionManager db = new DBConnectionManager();
        List<SoftwareDeveloper> developers = db.getAllDevelopers();

        Gson gson = new Gson();
        String json = gson.toJson(developers);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
