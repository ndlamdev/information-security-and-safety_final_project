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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       System.out.println(1);
        HttpSession session = request.getSession();
       ObjectMapper objectMapper = new ObjectMapper();
       System.out.println(12);
       Map<String, String> jsonMap = objectMapper.readValue(request.getReader(), new TypeReference<Map<String, String>>() {});
       System.out.println(123);
       String email = jsonMap.get("email");
       String password = jsonMap.get("password");
       String rePassword = jsonMap.get("rePassword");
       response.setContentType("application/json");
       UserService userService = UserService.getInstance();
        if (!userService.containsEmail(email)
                || email == null
                || email.equals("")) {
            Action.error(request, response);
            return;
        }

        if (!password.equals(rePassword)
                || (password.equals(rePassword) && (password == null || password.equals("")))) {
                  response.setStatus(500);
            response.getWriter().println("{\"message\": \"Password Change Error\"}");
        }
        int result = userService.resetPassword(email, BCrypt.hashpw(password, BCrypt.gensalt(12)));
        if (result != 0) {
            response.setStatus(202);
            response.getWriter().println("{\"message\": \"Password Change Success\"}");
        }else{
            response.setStatus(500);
            response.getWriter().println("{\"message\": \"Password Change Error\"}");
        }

    }
}
