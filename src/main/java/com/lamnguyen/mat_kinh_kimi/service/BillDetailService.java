package com.lamnguyen.mat_kinh_kimi.service;

import com.lamnguyen.mat_kinh_kimi.repository.impl.BillDetailRepositoryImpl;
import com.lamnguyen.mat_kinh_kimi.model.BillDetail;

import java.util.List;

public class BillDetailService {
    private static BillDetailService instance;
    private final BillDetailRepositoryImpl BILL_DETAIL_REPOSITORY;

    private BillDetailService() {
        BILL_DETAIL_REPOSITORY = BillDetailRepositoryImpl.getInstance();
    }

    public static BillDetailService getInstance() {
        return (instance = instance == null ? new BillDetailService() : instance);
    }

    public int getTotalQuantitySold(Integer productId) {
        return BILL_DETAIL_REPOSITORY.getTotalQuantitySold(productId);
    }

    public void insert(int id, List<BillDetail> details) {
        for (BillDetail billDetail : details) {
            billDetail.setBillId(id);
            BILL_DETAIL_REPOSITORY.insert(billDetail);
        }
    }

    public List<BillDetail> getBillDetails(Integer billId) {
        return BILL_DETAIL_REPOSITORY.getBillDetails(billId);
    }
}
