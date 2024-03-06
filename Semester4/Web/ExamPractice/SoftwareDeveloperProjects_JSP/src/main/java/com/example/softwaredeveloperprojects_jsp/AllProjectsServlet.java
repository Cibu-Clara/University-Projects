package com.example.softwaredeveloperprojects_jsp;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Project;
import utils.DBConnectionManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/all-projects")
public class AllProjectsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>All Projects</h1>");

        DBConnectionManager db = new DBConnectionManager();
        List<Project> list = db.getAllProjects();

        out.print("<table border='1' max-width='100%'");
        out.print("<tr><th>Id</th><th>Name</th><th>Description</th><th>Members</th><th>Project Manager ID</th></tr>");
        for(Project p:list){
            out.print("<tr><td>"+p.getId()+"</td><td>"+p.getName()+"</td><td>"+p.getDescription()+"</td><td>"+p.getMembers()+"</td><td>"+p.getProject_manager_id()+"</td></tr>");
        }
        out.print("</table>");
        out.close();
    }
}