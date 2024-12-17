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
import java.util.Arrays;
import java.util.Objects;

public class LockPublicKeyAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        var user = (User) request.getSession().getAttribute("user");
        var hashCodeVerify = (String) request.getSession().getAttribute("codeVerify");
        var mailCodeVerify = request.getParameter("mailCodeVerify");
        boolean result = false;
        if(BCrypt.checkpw(mailCodeVerify, hashCodeVerify)) {
            PublicKeyService publicKeyService = PublicKeyService.getInstance();
            result = publicKeyService.lockPublicKey(user.getId());
        }
        request.getSession().removeAttribute("codeVerify");
        JSONObject json = new JSONObject();
        json.put("lockKey", result);
        response.getWriter().println(json);
    }
}
