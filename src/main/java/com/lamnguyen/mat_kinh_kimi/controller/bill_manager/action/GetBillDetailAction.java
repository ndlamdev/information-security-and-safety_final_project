package com.lamnguyen.mat_kinh_kimi.controller.bill_manager.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.Bill;
import com.lamnguyen.mat_kinh_kimi.model.User;
import com.lamnguyen.mat_kinh_kimi.service.BillService;
import com.lamnguyen.mat_kinh_kimi.service.BillStatusService;
import com.lamnguyen.mat_kinh_kimi.service.PublicKeyService;
import com.lamnguyen.mat_kinh_kimi.service.UserService;
import com.lamnguyen.mat_kinh_kimi.util.enums.BillStatusEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

public class GetBillDetailAction implements Action {
    BillService billService = BillService.getInstance();
    BillStatusService billStatusService = BillStatusService.getInstance();

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, RuntimeException {
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

        var products = billService.getProductInBill(billId);

        User customer = UserService.getInstance().getUser(bill.getUserId());
        var signature = billService.findSignature(billId);
        PublicKey publicKey = null;
        try {
            publicKey = PublicKeyService.getInstance().getPublicKey(customer.getId());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("signature", signature);
        request.setAttribute("publicKey", publicKey != null ? new String(Base64.getEncoder().encode(publicKey.getEncoded())) : null);
        request.setAttribute("customer", customer);
        request.setAttribute("bill", bill);
        request.setAttribute("products", products);
        request.setAttribute("status", BillStatusEnum.findEnumByStatus(bill.getStatuses().getLast().getStatus()).nextStep());
        request.setAttribute("next-status", BillStatusEnum.findEnumByStatus(billStatusService.getLastStatus(billId).getFirst().getStatus()).nextStep());
        request.getRequestDispatcher("chi_tiet_hoa_don.jsp").forward(request, response);
    }
}
