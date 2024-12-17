package com.lamnguyen.mat_kinh_kimi.controller.public_key.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.User;
import com.lamnguyen.mat_kinh_kimi.service.PublicKeyService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class UploadKeyAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        Part filePart = request.getPart("publicKeyFile");
        var user = (User) request.getSession().getAttribute("user");
        boolean result = false;
        byte[] data;
        try (InputStream inputStream = filePart.getInputStream(); ) {
           String publicKey = Base64.getEncoder().encodeToString(inputStream.readAllBytes());
            PublicKeyService publicKeyService = PublicKeyService.getInstance();
            result = publicKeyService.uploadPublicKey(publicKey, user.getId());
        }

        JSONObject json = new JSONObject();
        json.put("uploadKey", result);
        response.getWriter().println(json);
    }
}
