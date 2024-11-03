package controller.dash_board;

import controller.Action;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DashBoardController", value = "/admin_pages/dashboard")
public class DashBoardController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String actionStr = request.getParameter("action");
        Action action = null;
        switch (actionStr){
            case "products" -> action = new GetProduct();
        }
        action.action(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String actionStr = request.getParameter("action");
        Action action = null;
        switch (actionStr){
            case "number-list-by-category" -> action = new NumberListByCategory();
            case "number-list-by-product" -> action = new NumberListByProduct();
        }
        action.action(request, response);
    }

}