package com.lamnguyen.mat_kinh_kimi.controller.address;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.controller.address.action.DistrictAction;
import com.lamnguyen.mat_kinh_kimi.controller.address.action.ProvinceAction;
import com.lamnguyen.mat_kinh_kimi.controller.address.action.WardAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddressController", value = "/address")
public class AddressController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Action actionAddress = switch (action) {
            case "provinces" -> new ProvinceAction();
            case "districts" -> new DistrictAction();
            case "wards" -> new WardAction();
            default -> throw new IllegalStateException("Unexpected value: " + action);
        };

        actionAddress.action(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
