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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/insert-multiple")
public class insertMultipleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }
        String json = sb.toString();

        String user = null;
        String sid=request.getParameter("id");
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("user")) {
                user = cookie.getValue();
                break;
            }
        }
        String date = String.valueOf(java.time.LocalDate.now());
        Db_manager db = new Db_manager();
        User userr = db.getUserFromName(user);
        int user_id = userr.getId();

        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String title = jsonObject.getString("title");
                String description = jsonObject.getString("description");
                String url = jsonObject.getString("url");

                Content object = new Content(date, title, description, url, user_id);
                db.insertContent(object);
            }



            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("Data added to the database successfully!");

        } catch (JSONException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("An error occurred: " + e.getMessage());
        }

        // Set the response status and send a response
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Data received successfully");
    }
}