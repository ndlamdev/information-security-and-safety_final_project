package com.lamnguyen.mat_kinh_kimi.controller.forget_password.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangePasswordAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rePassword = request.getParameter("re-password");
        UserService userService = UserService.getInstance();
        if (!userService.containsEmail(email)
                || email == null
                || email.equals("")
                || !session.getAttribute("email").equals(email)) {
            Action.error(request, response);
            return;
        }

        if (!password.equals(rePassword)
                || (password.equals(rePassword) && (password == null || password.equals("")))) {
            request.setAttribute("re-password-error", "Lỗi mật khẩu!");
            request.getRequestDispatcher("quen_mat_khau.jsp").forward(request, response);
            return;
        }

        int result = userService.resetPassword(email, BCrypt.hashpw(password, BCrypt.gensalt(12)));
        if (result != 0) {
            session.removeAttribute("email");
            session.removeAttribute("time");
            session.setAttribute("message", "Bạn đã thay đổi mật khẩu thành công!");
            response.sendRedirect("dang_nhap.jsp");
        }
    }
}
