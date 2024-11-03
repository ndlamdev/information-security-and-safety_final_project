package controller.bill_detail;

import controller.Action;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "BillDetail", value = "/bill_detail")
public class BillDetail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String actionString = request.getParameter("action");
        Action action = null;
        switch (actionString){
            case "see-detail" -> {
                action = new SeeDetail();
            }
        }

        action.action(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String actionString = request.getParameter("action");
        Action action = null;
        switch (actionString){
            case "save" -> {
                action = new SaveEditBill();
            }
            case "cancel-bill" -> {
                action = new CancelBill();
            }
        }

        action.action(request, response);
    }
}