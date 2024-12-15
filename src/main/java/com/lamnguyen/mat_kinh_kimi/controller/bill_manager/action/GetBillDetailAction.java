package com.lamnguyen.mat_kinh_kimi.controller.bill_manager.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.*;
import com.lamnguyen.mat_kinh_kimi.service.BillService;
import com.lamnguyen.mat_kinh_kimi.service.UserService;
import com.lamnguyen.mat_kinh_kimi.util.enums.BillStatusEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetBillDetailAction implements Action {
    BillService billService = BillService.getInstance();

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int billId = 0;
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

        var products = billService.getProductInBill(billId);

        User customer = UserService.getInstance().getUser(bill.getUserId());
        var signature = billService.findSignature(billId);
        var publicKey = UserService.getInstance().getPublicKey(customer.getId());
        request.setAttribute("signature", signature);
        request.setAttribute("publicKey", publicKey);
        request.setAttribute("customer", customer);
        request.setAttribute("bill", bill);
        request.setAttribute("products", products);
        request.setAttribute("status", BillStatusEnum.findEnumByStatus(bill.getStatuses().getLast().getStatus()).nextStep());
        request.getRequestDispatcher("chi_tiet_hoa_don.jsp").forward(request, response);
    }
}
