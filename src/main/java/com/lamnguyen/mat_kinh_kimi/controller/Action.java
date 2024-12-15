package com.lamnguyen.mat_kinh_kimi.controller;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
public interface Action {
    void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    static void error(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("error.jsp");
    }

    static void errorAPI(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setStatus(404);
        response.getWriter().println(new JSONObject());
    }
}
