package com.lamnguyen.mat_kinh_kimi.controller.public_key;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
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

     try {
         // 1. Retrieve the "action" parameter
         String actionString = request.getParameter("action");

         // 2. Process the uploaded file
         Part filePart = request.getPart("publicKeyFile"); // Get the file part
         request.setAttribute("publicKey", filePart);

         Action action = switch (actionString) {
             case "upload-key" ->  new UploadKeyAction();
             case "lock-key" -> new LockPublicAction();
             default -> throw new NullPointerException();
         };

         action.action(request, response);
     }catch (Exception e) {
         e.printStackTrace();
         response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error uploading file.");
     }

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        action(req, resp);
    }
}
