package controller.forget_password;

import controller.Action;
import model.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ForgetPasswordController", value = "/forget_password")
public class ForgetPasswordController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionStr = request.getParameter("action");
        Action action = null;
        switch (actionStr) {
            case "verify" -> {
                action = new ForgetPasswordVerify();
            }
        }

        if (action == null) Action.error(request, response);
        else action.action(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionStr = request.getParameter("action");
        Action action = null;
        switch (actionStr) {
            case "check_mail" -> {
                action = new CheckEmail();
            }
            case "change_password" -> {
                action = new ChangePassword();
            }
        }

        if (action == null) Action.error(request, response);
        else action.action(request, response);
    }
}