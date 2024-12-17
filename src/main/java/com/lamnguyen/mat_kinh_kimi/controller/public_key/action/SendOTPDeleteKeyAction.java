package com.lamnguyen.mat_kinh_kimi.controller.public_key.action;

import com.lamnguyen.mat_kinh_kimi.config.mail.SendMail;
import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class SendOTPDeleteKeyAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        LocalDateTime sendMailTime = (LocalDateTime) request.getSession().getAttribute("sendMailTime");
        if(sendMailTime != null && request.getParameter("resend") == null) return;

        request.getSession().setAttribute("sendMailTime", LocalDateTime.now());
        var user = (User) request.getSession().getAttribute("user");
        String codeVerify = UUID.randomUUID().toString().substring(0, 4);
        String hashCode =  BCrypt.hashpw(codeVerify, BCrypt.gensalt(4));
        SendMail.Send(user.getEmail(), "Xác Nhận Hủy Khóa Công Cộng!", SendMail.getFormDeletePublicKey(user.getEmail(), codeVerify));
        request.getSession().setAttribute("codeVerify", hashCode);
    }
}
