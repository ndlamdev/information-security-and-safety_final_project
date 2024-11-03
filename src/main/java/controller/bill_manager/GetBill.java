package controller.bill_manager;

import controller.Action;
import model.dto.BillManage;
import model.service.BillService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetBill implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BillService billService = BillService.getInstance();
        String id = getLastParameterValue(request, "id");
        String name = getLastParameterValue(request, "name");
        String status = getLastParameterValue(request, "status");
        int page;
        try {
            page = Integer.parseInt(getLastParameterValue(request, "page"));
        } catch (NumberFormatException e) {
            page = 1;
        }
        int limit = 7;
        int offset = (page - 1) * limit;

        List<BillManage> billManages = billService.getBillManages(id, name, status, limit, offset);
        int totalBill = billService.totalBillManage(id, name, status);
        int totalPages = totalBill % 7 == 0 ? totalBill / 7 : (totalBill / 7) + 1;
        JSONObject json = new JSONObject();
        json.put("bills", billManages);
        json.put("pages", totalPages);
        json.put("total", totalBill);
        response.getWriter().println(json.toString());
    }

    public String getLastParameterValue(HttpServletRequest request, String name) {
        String[] values = request.getParameterValues(name);
        return values == null ? "" : values[values.length - 1];
    }
}
