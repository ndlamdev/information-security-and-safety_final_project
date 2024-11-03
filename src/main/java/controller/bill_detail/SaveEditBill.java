package controller.bill_detail;

import controller.Action;
import model.bean.Bill;
import model.service.AddressService;
import model.service.BillService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveEditBill implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String billIdString = request.getParameter("bill-id"),
                name = request.getParameter("name"),
                phoneNumber = request.getParameter("phone-number"),
                email = request.getParameter("email"),
                address = request.getParameter("address"),
                provinceCodeString = request.getParameter("province-code"),
                districtCodeString = request.getParameter("district-code"),
                wardCodeString = request.getParameter("ward-code");
        int billId = 0, provinceCode = 0, districtCode = 0, wardCode = 0;
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
        Bill bill = new Bill();
        bill.setId(billId);
        bill.setUserName(name);
        bill.setPhoneNumber(phoneNumber);
        bill.setEmail(email);
        bill.setAddress(address);
        bill.setCodeProvince(provinceCode);
        bill.setCodeDistrict(districtCode);
        bill.setCodeWard(wardCode);
        billService.updateContact(bill);

        String addressDetails = new AddressService().getAddress(bill.getCodeProvince(), bill.getCodeDistrict(), bill.getCodeWard()) +
                "<br>" + bill.getAddress();
        JSONObject json = new JSONObject();
        json.put("addressDetail", addressDetails);
        response.getWriter().println(json);
    }
}
