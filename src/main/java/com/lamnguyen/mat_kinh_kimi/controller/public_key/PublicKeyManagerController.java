package com.lamnguyen.mat_kinh_kimi.controller.public_key;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.public_key.action.LockPublicAction;
import com.lamnguyen.mat_kinh_kimi.controller.public_key.action.UploadKeyAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(name = "PublicKey", value = "/public-key")
public class PublicKeyManagerController implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String actionString = request.getParameter("action");
        Action action = switch (actionString) {
            case "upload-key" ->  new UploadKeyAction();
            case "lock-key" -> new LockPublicAction();
            default -> throw new NullPointerException();
        };

        action.action(request, response);
    }
}
