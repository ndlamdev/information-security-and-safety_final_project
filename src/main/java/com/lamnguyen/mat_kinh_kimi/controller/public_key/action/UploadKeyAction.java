package com.lamnguyen.mat_kinh_kimi.controller.public_key.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.User;
import com.lamnguyen.mat_kinh_kimi.service.PublicKeyService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UploadKeyAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        Part filePart = request.getPart("publicKeyFile"); // Get the file part
        var user = (User) request.getSession().getAttribute("user");
        boolean result = false;
        if(filePart.getContentType().equals("text/plain")) {
            StringBuilder publicKey = new StringBuilder();
            try (InputStream inputStream = filePart.getInputStream();
                 Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) { // Specify encoding
                scanner.useDelimiter("\\A"); // Match the entire input
                while (scanner.hasNextLine()) {
                    publicKey.append(scanner.nextLine());
                }
            }

            PublicKeyService publicKeyService = PublicKeyService.getInstance();
            result = publicKeyService.uploadPublicKey(publicKey.toString(), user.getId());
        }

        JSONObject json = new JSONObject();
        json.put("uploadKey", result);
        response.getWriter().println(json);
    }
}
