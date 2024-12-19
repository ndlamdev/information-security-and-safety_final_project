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

public class UploadKeyAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        Part filePart = request.getPart("publicKeyFile");
        var user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            Action.errorAPI(request, response, new JSONObject() {{
                put("message", "Vui lòng đăng nhập trước khi thực hiện cập nhật khóa!");
            }});
            return;
        }
        if (PublicKeyService.getInstance().existsPublicKey(user.getId())) {
            Action.errorAPI(request, response, new JSONObject() {{
                put("message", "Bạn đã cập khóa công khai!");
            }});
            return;
        }

        boolean result;
        try {
            InputStream inputStream = filePart.getInputStream();
            PublicKeyService publicKeyService = PublicKeyService.getInstance();
            result = publicKeyService.uploadPublicKey(PublicKeyService.readPublicKey(inputStream), user.getId());
            System.out.println(result);
            if (!result) {
                Action.errorAPI(request, response, new JSONObject() {{
                    put("message", "Cập nhật khóa thất bại!");
                }});
                return;
            }
        } catch (Exception e) {
            Action.errorAPI(request, response, new JSONObject() {{
                put("message", "Khóa không hợp lệ! Vui lòng nhập file khóa hợp lệ hoặc tạo khóa mới bằng phần mềm của chúng tôi!");
            }});
            return;
        }

        response.getWriter().println(new JSONObject() {{
            put("message", "Cập nhật khóa thành công!");
        }});
    }
}
