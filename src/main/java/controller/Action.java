package controller;

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
}
