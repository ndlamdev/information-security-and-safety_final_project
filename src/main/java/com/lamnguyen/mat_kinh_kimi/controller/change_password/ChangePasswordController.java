package com.lamnguyen.mat_kinh_kimi.controller.change_password;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ChangePassword", value = "/user/changePassword")
public class ChangePasswordController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(request.getReader(), new TypeReference<Map<String, String>>() {});

        String email = jsonMap.get("email");
        String password = jsonMap.get("password");
        String rePassword = jsonMap.get("rePassword");

        response.setContentType("application/json");

        if (email == null || email.isEmpty() || !UserService.getInstance().containsEmail(email)) {
            Action.error(request, response);
            return;
        }
        if (isInvalidPassword(password, rePassword)) {
            sendErrorResponse(response, "Password Change Error", 500);
            return;
        }

        int result = UserService.getInstance().resetPassword(email, BCrypt.hashpw(password, BCrypt.gensalt(12)));
        if (result != 0) {
            sendSuccessResponse(response, "Password Change Success", 202);
        } else {
            sendErrorResponse(response, "Password Change Error", 500);
        }
    }

    private boolean isInvalidPassword(String password, String rePassword) {
        return password == null || password.isEmpty() || !password.equals(rePassword);
    }

    private void sendSuccessResponse(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.getWriter().println("{\"message\": \"" + message + "\"}");
    }

    private void sendErrorResponse(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.getWriter().println("{\"message\": \"" + message + "\"}");
    }
}
