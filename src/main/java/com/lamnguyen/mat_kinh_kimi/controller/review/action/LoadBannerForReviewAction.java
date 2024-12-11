package com.lamnguyen.mat_kinh_kimi.controller.review.action;

import com.lamnguyen.mat_kinh_kimi.controller.Action;
import com.lamnguyen.mat_kinh_kimi.model.BannerImage;
import com.lamnguyen.mat_kinh_kimi.service.BannerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoadBannerForReviewAction implements Action {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BannerImage logo = BannerService.getInstance().getBannerByDescription("%banner%logo%");
        request.setAttribute("logo", logo);
        request.getRequestDispatcher("danh_gia.jsp").forward(request, response);
    }
}
