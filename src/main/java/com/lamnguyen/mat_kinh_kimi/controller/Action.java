package com.lamnguyen.mat_kinh_kimi.controller;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@MultipartConfig
public interface Action {
    void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, RuntimeException;

    static void error(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("error.jsp");
    }

    static void errorAPI(HttpServletRequest request, HttpServletResponse response) throws IOException {
        errorAPI(request, response, new JSONObject());
    }

    static void errorAPI(HttpServletRequest request, HttpServletResponse response, JSONObject data) throws IOException {
        response.setContentType("application/json");
        response.setStatus(404);
        response.getWriter().println(data);
    }
}
