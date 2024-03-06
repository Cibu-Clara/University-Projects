package com.example.jsp_exam;

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

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {
    public Login() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username.equals("")) {
            request.getSession().setAttribute("error", "Username must not be null!");
            request.getRequestDispatcher("login.jsp").include(request, response);
        } else if (password.isEmpty()) {
            request.getSession().setAttribute("error", "Password must not be null!");
            request.getRequestDispatcher("login.jsp").include(request, response);
        } else if (password.length() < 3) {
            request.getSession().setAttribute("error", "Password is not strong enough! It must have more than 2 characters!");
            request.getRequestDispatcher("login.jsp").include(request, response);
        }
        else {
            Db_manager dbManager = new Db_manager();
            User user = dbManager.Authentication(username, password);
            int inttt = dbManager.auto(username, password);



            if (user != null) {
                response.addCookie(new Cookie("user", username));

                Db_manager db = new Db_manager();
                int role = db.getUserFromName(username).getRole();

                if(role == 1)
                    response.sendRedirect("contentCreatorsPage.jsp");
                else if( role == 2){
                    response.sendRedirect("contentViewers.jsp");
                }
            } else {
                request.getSession().setAttribute("error", "Username or password invalid!");
                request.getRequestDispatcher("login.jsp").include(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher("login/login.jsp").include(request, response);
    }
}