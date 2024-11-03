package controller.register;

import controller.Action;
import helper.SendMail;
import model.bean.User;
import model.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@WebServlet(name = "RegisterController", value = "/register")
public class RegisterController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String actionStr = request.getParameter("action");
        Action action = null;
        switch (actionStr) {
            case "verify" -> {
                action = new RegisterVerify();
            }
        }
        if (action == null) Action.error(request, response);
        else action.action(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String actionStr = request.getParameter("action");
        Action action = null;
        switch (actionStr) {
            case "register" -> {
                action = new Register();
            }
        }
        if (action == null) Action.error(request, response);
        else action.action(request, response);
    }
}