/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:48 AM - 15/12/2024
 * User: kimin
 **/

package com.lamnguyen.mat_kinh_kimi.controller.bill;

import com.lamnguyen.mat_kinh_kimi.config.mail.SendMail;
import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.domain.dto.Signature;
import com.lamnguyen.mat_kinh_kimi.model.Bill;
import com.lamnguyen.mat_kinh_kimi.model.BillStatus;
import com.lamnguyen.mat_kinh_kimi.service.AddressService;
import com.lamnguyen.mat_kinh_kimi.service.BillService;
import com.lamnguyen.mat_kinh_kimi.service.BillStatusService;
import com.lamnguyen.mat_kinh_kimi.util.enums.BillStatusEnum;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;


@WebServlet(name = "ConfirmPayController", value = "/confirm_pay")
public class ConfirmPayController extends HttpServlet implements Action {
    BillStatusService billStatusService = BillStatusService.getInstance();
    BillService billService = BillService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("index.jsp");
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
        if (back == null) {
            response.sendRedirect("index.jsp");
            return;
        }
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
                .date(LocalDateTime.now())
                .status(BillStatusEnum.WAIL_CONFiRM.getStatus())
                .describe("Đơn hàng của bạn đang chờ xác nhận!")
                .build());
        var update = billService.updateSignature(billObj.getId(), Signature.builder().signature(signature).algorithm(algorithm).verify(false).build());
        if (update == 0) {
            response.sendRedirect(back.toString());
            return;
        }

        session.removeAttribute("products");
        session.removeAttribute("file");
        session.removeAttribute("back");
        String addressDetails = AddressService.getInstance().getAddress(billObj.getCodeProvince(), billObj.getCodeDistrict(), billObj.getCodeWard()) +
                                "<br>" + billObj.getAddress();
        request.setAttribute("addressDetails", addressDetails);
        String url = request.getRequestURL().toString().replace("xac_nhan_thanh_toan.jsp", "policy_pages/kiem_tra_don_hang.jsp");
        SendMail.SendMailWithImage(billObj.getEmail(), "Thanh toán thành công", SendMail.getFormBill(url, billObj, addressDetails));
        request.getRequestDispatcher("thanh_toan_thanh_cong.jsp").forward(request, response);
    }
}
