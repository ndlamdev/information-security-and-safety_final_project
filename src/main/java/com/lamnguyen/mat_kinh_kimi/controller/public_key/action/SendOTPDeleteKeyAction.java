package com.lamnguyen.mat_kinh_kimi.controller.public_key.action;

import com.lamnguyen.mat_kinh_kimi.config.mail.SendMail;
import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.User;
import com.lamnguyen.mat_kinh_kimi.service.PublicKeyService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
public class SendOTPDeleteKeyAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        var user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            Action.errorAPI(request, response, new JSONObject() {{
                put("message", "Vui lòng đăng nhập trước khi thực hiện cập nhật khóa!");
            }});
            return;
        }

        if (!PublicKeyService.getInstance().existsPublicKey(user.getId())) {
            Action.errorAPI(request, response, new JSONObject() {{
                put("message", "Bạn không có khóa để hủy!");
            }});
            return;
        }


        LocalDateTime sendMailTime = (LocalDateTime) request.getSession().getAttribute("time");
        if (sendMailTime != null && LocalDateTime.now().minusMinutes(5).isBefore(sendMailTime)) {
            response.getWriter().println(new JSONObject() {{
                put("time", sendMailTime);
                put("send", false);
            }});
            return;
        }

        var time = LocalDateTime.now();
        request.getSession().setAttribute("time", time);
        String codeVerify = UUID.randomUUID().toString().substring(0, 4);
        String hashCode = BCrypt.hashpw(codeVerify, BCrypt.gensalt(4));
        SendMail.Send(user.getEmail(), "Xác Nhận Hủy Khóa Công Cộng!", SendMail.getFormDeletePublicKey(user.getEmail(), codeVerify));
        request.getSession().setAttribute("code", hashCode);
        response.getWriter().println(new JSONObject() {{
            put("time", time);
            put("send", true);
        }});
    }
}
