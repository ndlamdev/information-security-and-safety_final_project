package controller.address;

import controller.Action;
import model.service.AddressService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DistrictAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int code = 0;
        try {
            code = Integer.parseInt(request.getParameter("code"));
        }catch (NumberFormatException e){

        }
        AddressService service = new AddressService();
        JSONObject json = new JSONObject();
        json.put("districts", service.getAllDistrict(code));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}
