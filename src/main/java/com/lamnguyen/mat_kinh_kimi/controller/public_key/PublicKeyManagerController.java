package com.lamnguyen.mat_kinh_kimi.controller.public_key;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.public_key.action.ExistsPublicKeyAction;
import com.lamnguyen.mat_kinh_kimi.controller.public_key.action.LockPublicKeyAction;
import com.lamnguyen.mat_kinh_kimi.controller.public_key.action.SendOTPDeleteKeyAction;
import com.lamnguyen.mat_kinh_kimi.controller.public_key.action.UploadKeyAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(name = "publicKey", value = "/public-key")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 30   // 30MB
)
public class PublicKeyManagerController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String actionString = request.getParameter("action");
        Action action = switch (actionString) {
            case "exists-key" -> new ExistsPublicKeyAction();
            default -> throw new NullPointerException();
        };
        action.action(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String actionString = request.getParameter("action");
        Action action = switch (actionString) {
            case "upload-key" -> new UploadKeyAction();
            case "lock-key" -> new LockPublicKeyAction();
            case "send-otp-delete-key" -> new SendOTPDeleteKeyAction();
            default -> throw new NullPointerException();
        };
        action.action(request, response);
    }
}
