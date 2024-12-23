package com.lamnguyen.mat_kinh_kimi.controller.bill_manager.action;

import com.lamnguyen.mat_kinh_kimi.config.mail.SendMail;
import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.domain.dto.Signature;
import com.lamnguyen.mat_kinh_kimi.model.Bill;
import com.lamnguyen.mat_kinh_kimi.model.BillStatus;
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
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
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
        isValidSignature(signature, bill, request);
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
        request.setAttribute("next-status", BillStatusEnum.findEnumByStatus(bill.getStatuses().getLast().getStatus()).nextStep());
        request.getRequestDispatcher("chi_tiet_hoa_don.jsp").forward(request, response);
    }

    public static boolean isValidSignature(Signature signature, Bill bill, HttpServletRequest request) {
        try {
            if (BillStatusEnum.findEnumByStatus(bill.getStatuses().getLast().getStatus()).getStep() == 1
                && signature.getVerify()
                && !VerifyBillAction.isValidSignature(bill.getId(), request)) {
                var status = BillStatus.builder()
                        .status(BillStatusEnum.NOT_SIGN.getStatus())
                        .describe("Đơn hàng của bạn đã thay đổi. Vui lòng ký lại!")
                        .date(LocalDateTime.now())
                        .billId(bill.getId())
                        .canEdit(true)
                        .build();
                BillStatusService.getInstance().insert(status);
                SendMail.Send(bill.getEmail(), "Yêu cầu ký lại!",
                        "<div style=\"max-width: 500px; margin: auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);\">"
                        + "<h2 style=\"margin-bottom: 20px; color: #333;\">Đơn hàng của bạn đã bị hệ thống thay đổi.</h2>"
                        + "<p style=\"margin-bottom: 20px; color: #555;\">Đơn hàng của bạn đã bị thay đổi. Vui lòng kiểm tra lại thông tin và thực hiện ký lại để xác nhận đơn hàng. Nhấn vào nút bên dưới để được chuyển hướng đến trang ký xác nhận.</p>"
                        + "<a href=\"http://localhost:8080/mat_kinh_kimi/bill_history?action=see-detail&bill-id=" + bill.getId() + "\" style=\"display:block;width:100%;padding:10px;font-size:16px;color:#fff;background-color:#007bff;border:none;border-radius:5px;text-decoration: none;text-align: center;\">Ký lại đơn hàng</a>"
                        + "</div>"
                );
                SendMail.Send("matkinhkimi@gmail.com", "Đơn hàng bị thay đổi đáng nghi ngờ.",
                        "<div style=\"max-width: 500px; margin: auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);\">"
                                + "<h2 style=\"margin-bottom: 20px; color: #333;\">Đơn hàng có id #" + bill.getId() + " bị thay đổi một cách đáng ngờ.</h2>"
                                + "<p style=\"margin-bottom: 20px; color: #555;\">Một đang hàng đã được xác thực nghi bị thay đổi ở dưới database mà không thông quá các controller của hệ thống một cách đáng nghi ngờ. Vui lòng kiểm tra lại thông tin đơn hàng. Nhấn vào nút bên dưới để được chuyển hướng đến trang chi tiết đơn hàng bị thay đổi.</p>"
                                + "<a href=\"http://localhost:8080/mat_kinh_kimi/admin_pages/bill_manager?action=see-detail&bill-id=" + bill.getId() + "\" style=\"display:block;width:100%;padding:10px;font-size:16px;color:#fff;background-color:#007bff;border:none;border-radius:5px;text-decoration: none;text-align: center;\">Kiểm tra đơn hàng</a>"
                                + "</div>"
                );
                signature.setSignature(null);
                signature.setAlgorithm(null);
                signature.setVerify(false);
                BillService.getInstance().updateSignature(bill.getId(), signature);
                bill.getStatuses().add(status);
                return false;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | SignatureException | InvalidKeyException |
                 NoSuchProviderException | IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
