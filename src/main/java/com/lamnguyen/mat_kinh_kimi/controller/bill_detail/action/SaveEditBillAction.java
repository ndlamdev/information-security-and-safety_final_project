package com.lamnguyen.mat_kinh_kimi.controller.bill_detail.action;

import com.lamnguyen.mat_kinh_kimi.config.mail.SendMail;
import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.domain.dto.Signature;
import com.lamnguyen.mat_kinh_kimi.model.Bill;
import com.lamnguyen.mat_kinh_kimi.model.BillStatus;
import com.lamnguyen.mat_kinh_kimi.service.AddressService;
import com.lamnguyen.mat_kinh_kimi.service.AutoSendMailNotifyService;
import com.lamnguyen.mat_kinh_kimi.service.BillService;
import com.lamnguyen.mat_kinh_kimi.service.BillStatusService;
import com.lamnguyen.mat_kinh_kimi.util.enums.BillStatusEnum;
import com.lamnguyen.mat_kinh_kimi.util.helper.DocumentHelper;
import com.lamnguyen.mat_kinh_kimi.util.mapper.BillMapper;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@SuppressWarnings("MalformedFormatString")
public class SaveEditBillAction implements Action {
    private final AddressService ADDRESS_SERVICE;
    BillService service = BillService.getInstance();
    private boolean isAdmin;

    public SaveEditBillAction(boolean admin) {
        ADDRESS_SERVICE = AddressService.getInstance();
        this.isAdmin = admin;
    }

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String billIdString = request.getParameter("bill-id"),
                name = request.getParameter("name"),
                phoneNumber = request.getParameter("phone-number"),
                email = request.getParameter("email"),
                address = request.getParameter("address"),
                provinceCodeString = request.getParameter("province-code"),
                districtCodeString = request.getParameter("district-code"),
                wardCodeString = request.getParameter("ward-code"),
                oldSignature = request.getParameter("old-signature");
        int billId;
        int provinceCode;
        int districtCode;
        int wardCode;
        try {
            billId = Integer.parseInt(billIdString);
            provinceCode = Integer.parseInt(provinceCodeString);
            districtCode = Integer.parseInt(districtCodeString);
            wardCode = Integer.parseInt(wardCodeString);
        } catch (NumberFormatException e) {
            response.setStatus(404);
            response.getWriter().println(new JSONObject());
            return;
        }

        BillService billService = BillService.getInstance();
        var signature = billService.findSignature(billId);
        if (!isAdmin && (signature.getVerify() || (signature.getSignature() != null && !signature.getSignature().equals(oldSignature)))) {
            Action.errorAPI(request, response);
            return;
        }

        Bill bill = new Bill();
        bill.setId(billId);
        bill.setUserName(name);
        bill.setPhoneNumber(phoneNumber);
        bill.setEmail(email);
        bill.setAddress(address);
        bill.setCodeProvince(provinceCode);
        bill.setCodeDistrict(districtCode);
        bill.setCodeWard(wardCode);
        var result = billService.updateContact(bill);
        if (result == -1) {
            Action.errorAPI(request, response);
            return;
        }

        if (result == 0) {
            JSONObject json = new JSONObject();
            json.put("result", result);
            response.getWriter().println(json);
            return;
        }
        billService.updateSignature(billId, Signature.builder().verify(false).build());

        String addressDetails = ADDRESS_SERVICE.getAddress(bill.getCodeProvince(), bill.getCodeDistrict(), bill.getCodeWard()) +
                                "<br>" + bill.getAddress();
        JSONObject json = new JSONObject();
        json.put("addressDetail", addressDetails);
        json.put("result", result);
        DocumentHelper.createBillFileText(BillMapper.billDTO(service.getBill(billId), service.getProductInBill(billId)), request);
        if (signature.getSignature() != null) {
            var status = BillStatus.builder()
                    .status(BillStatusEnum.NOT_SIGN.getStatus())
                    .describe("Đơn hàng của bạn đã thay đổi. Vui lòng ký lại!")
                    .date(LocalDateTime.now())
                    .billId(billId)
                    .canEdit(true)
                    .build();
            BillStatusService.getInstance().insert(status);
            json.put("status", new JSONObject(status));

            AutoSendMailNotifyService.getInstance().addAutoSendMail(email, billId);
            if (isAdmin) adminAction(email, billId);
            else userAction(email, billId);
        }

        response.getWriter().println(json);
    }

    private void adminAction(String email, int id) {
        SendMail.Send(email, "Yêu cầu ký lại!",
                "<div style=\"max-width: 500px; margin: auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);\">"
                + "<h2 style=\"margin-bottom: 20px; color: #333;\">Đơn hàng của bạn đã bị hệ thống thay đổi.</h2>"
                + "<p style=\"margin-bottom: 20px; color: #555;\">Đơn hàng của bạn đã bị thay đổi. Vui lòng kiểm tra lại thông tin và thực hiện ký lại để xác nhận đơn hàng. Nhấn vào nút bên dưới để được chuyển hướng đến trang ký xác nhận.</p>"
                + "<a href=\"http://localhost:8080/mat_kinh_kimi/bill_history?action=see-detail&bill-id=" + id + "\" style=\"display:block;width:100%;padding:10px;font-size:16px;color:#fff;background-color:#007bff;border:none;border-radius:5px;text-decoration: none;text-align: center;\">Ký lại đơn hàng</a>"
                + "</div>"
        );
    }

    private void userAction(String email, int id) {
        SendMail.Send(email, "Ký lại đơn hàng!",
                "<div style=\"max-width: 500px; margin: auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);\">"
                + "<h2 style=\"margin-bottom: 20px; color: #333;\">Đơn hàng của bạn đã được thay đổi khi đơn hàng đã xác thực.</h2>"
                + "<p style=\"margin-bottom: 20px; color: #555;\">Đơn hàng của bạn đã bị thay đổi. Vui lòng kiểm tra lại thông tin và thực hiện ký lại để xác nhận đơn hàng. Nhấn vào nút bên dưới để được chuyển hướng đến trang ký xác nhận.</p>"
                + "<a href=\"http://localhost:8080/mat_kinh_kimi/bill_history?action=see-detail&bill-id=" + id + "\" style=\"display:block;width:100%;padding:10px;font-size:16px;color:#fff;background-color:#007bff;border:none;border-radius:5px;text-decoration: none;text-align: center;\">Ký lại đơn hàng</a>"
                + "</div>"
        );
    }
}
