/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:48 AM - 15/12/2024
 * User: kimin
 **/

package com.lamnguyen.mat_kinh_kimi.controller.bill;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.Bill;
import com.lamnguyen.mat_kinh_kimi.model.BillStatus;
import com.lamnguyen.mat_kinh_kimi.service.BillService;
import com.lamnguyen.mat_kinh_kimi.service.BillStatusService;
import com.lamnguyen.mat_kinh_kimi.util.enums.BillStatusEnum;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "ConfirmPayController", value = "/confirm_pay")
public class ConfirmPayController extends HttpServlet implements Action {
    BillStatusService billStatusService = BillStatusService.getInstance();
    BillService billService = BillService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        action(request, response);
    }

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var session = request.getSession();
        var bill = session.getAttribute("billPayed");
        var back = session.getAttribute("back");
        if (bill == null) {
            response.sendRedirect(back.toString());
            return;
        }
        var billObj = (Bill) bill;
        var signature = request.getParameter("signature");
        var algorithm = request.getParameter("algorithm");
        billStatusService.insert(BillStatus.builder()
                .canEdit(true)
                .billId(billObj.getId())
                .status(BillStatusEnum.WAIL_CONFiRM.getStatus())
                .describe("Đơn hàng của bạn đang chờ xác nhận!")
                .build());
        var update = billService.updateSignature(billObj.getId(), algorithm, signature);
        if (update == 0) {
            response.sendRedirect(back.toString());
            return;
        }

        session.removeAttribute("products");
        session.removeAttribute("file");
        session.removeAttribute("back");
        session.removeAttribute("billPayed");
        request.getRequestDispatcher("thanh_toan_thanh_cong.jsp").forward(request, response);
    }
}
