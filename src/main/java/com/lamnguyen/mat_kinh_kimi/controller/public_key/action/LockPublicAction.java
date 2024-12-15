package com.lamnguyen.mat_kinh_kimi.controller.public_key.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.User;
import com.lamnguyen.mat_kinh_kimi.service.PublicKeyService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

public class LockPublicAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        var user = (User) request.getSession().getAttribute("user");
        Integer[] date = Arrays.stream(request.getParameter("date").split("/")).map(Integer::parseInt).toArray(Integer[]::new);
        int hour = Integer.parseInt(request.getParameter("hour"));
        int second = Integer.parseInt(request.getParameter("second"));
        int milli = Integer.parseInt(request.getParameter("milli"));
        LocalDateTime dateTime = LocalDateTime.of(date[0], date[1], date[2], hour, second, milli);
        PublicKeyService publicKeyService = PublicKeyService.getInstance();
        boolean result = publicKeyService.lockPublicKey(user.getId(), dateTime);

        JSONObject json = new JSONObject();
        json.put("upload-key", result);
        response.getWriter().println(json);
    }
}
