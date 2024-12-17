package com.lamnguyen.mat_kinh_kimi.controller.bill_detail.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.Bill;
import com.lamnguyen.mat_kinh_kimi.model.ProductCart;
import com.lamnguyen.mat_kinh_kimi.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SeeDetailAction implements Action {
    BillService billService = BillService.getInstance();

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int billId;
        try {
            billId = Integer.parseInt(request.getParameter("bill-id"));
        } catch (NumberFormatException e) {
            Action.error(request, response);
            return;
        }

        Bill bill = billService.getBill(billId);
        if (bill == null) {
            Action.error(request, response);
            return;
        }

        List<ProductCart> products = billService.getProductInBill(billId);

        request.setAttribute("bill", bill);
        request.setAttribute("products", products);
        request.setAttribute("file", "bill_" + bill.getId() + ".bills");
        request.getRequestDispatcher("chi_tiet_hoa_don.jsp").forward(request, response);
    }
}
