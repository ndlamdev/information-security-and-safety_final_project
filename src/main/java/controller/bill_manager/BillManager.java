package controller.bill_manager;

import controller.Action;
import controller.bill_detail.CancelBill;
import controller.bill_detail.SaveEditBill;
import controller.bill_detail.SeeDetail;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "BillManager", value = "/admin_pages/bill_manager")
public class BillManager extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String actionStr = request.getParameter("action");
        Action action = null;
        switch (actionStr) {
            case "get" -> action = new GetBill();
            case "see-detail" -> action = new GetBillDetail();
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