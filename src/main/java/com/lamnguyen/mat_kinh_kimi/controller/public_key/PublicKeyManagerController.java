package com.lamnguyen.mat_kinh_kimi.controller.public_key;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.public_key.action.ExistsPublicKeyAction;
import com.lamnguyen.mat_kinh_kimi.controller.public_key.action.LockPublicAction;
import com.lamnguyen.mat_kinh_kimi.controller.public_key.action.UploadKeyAction;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
@WebServlet(name = "publicKey", value = "/public-key")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 *30   // 30MB
)
public class PublicKeyManagerController extends HttpServlet implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String actionString = request.getParameter("action");
        Action action = switch (actionString) {
            case "upload-key" -> new UploadKeyAction();
            case "lock-key" -> new LockPublicAction();
            case "exists-key" -> new ExistsPublicKeyAction();
            default -> throw new NullPointerException();
        };
         action.action(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        action(req, resp);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        action(req, resp);
    }
}
