package com.lamnguyen.mat_kinh_kimi.controller.public_key.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.User;
import com.lamnguyen.mat_kinh_kimi.service.PublicKeyService;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class LockPublicKeyAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        boolean result = false;
        var sendMailTime = (LocalDateTime) request.getSession().getAttribute("time");
        var user = (User) request.getSession().getAttribute("user");
        var code = (String) request.getSession().getAttribute("code");
        var verifyCode = request.getParameter("verifyCode");
        sendMailTime = Objects.requireNonNullElse(sendMailTime, LocalDateTime.of(2000, 1, 1, 0, 0));
        var equals = BCrypt.checkpw(verifyCode, code);
        var timeOut = timeOut(sendMailTime);

        if (timeOut) {
            request.getSession().removeAttribute("time");
            request.getSession().removeAttribute("code");
        }

        if (user == null || timeOut || !equals) {
            Action.errorAPI(request, response);
            return;
        }

        PublicKeyService publicKeyService = PublicKeyService.getInstance();
        result = publicKeyService.lockPublicKey(user.getId());

        JSONObject json = new JSONObject();
        json.put("lockKey", result);
        response.getWriter().println(json);
    }

    private boolean timeOut(LocalDateTime sendMailTime) {
        return sendMailTime.until(LocalDateTime.now(), ChronoUnit.SECONDS) > 600;
    }
}
