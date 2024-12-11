package com.lamnguyen.mat_kinh_kimi.service;

import com.lamnguyen.mat_kinh_kimi.repository.impl.BillStatusRepositoryImpl;
import com.lamnguyen.mat_kinh_kimi.model.BillStatus;

import java.time.LocalDateTime;
import java.util.List;

public class BillStatusService {
    private static BillStatusService instance;
    private final BillStatusRepositoryImpl BILL_STATUS_REPOSITORY;

    private BillStatusService() {
        BILL_STATUS_REPOSITORY = BillStatusRepositoryImpl.getInstance();
    }

    public static BillStatusService getInstance() {
        return (instance = instance == null ? new BillStatusService() : instance);
    }

    public void insert(BillStatus status) {
        BILL_STATUS_REPOSITORY.insert(status);
    }

    public BillStatus getInfDateStatus(int billId) {
        return BILL_STATUS_REPOSITORY.getInfDateStatus(billId);
    }

    public List<BillStatus> getLastStatus(Integer id) {
        return BILL_STATUS_REPOSITORY.getLastBillStatus(id);
    }

    public List<BillStatus> getBillStatus(Integer id) {
        return BILL_STATUS_REPOSITORY.getBillStatus(id);
    }

    public LocalDateTime getDateOrderBill(Integer id) {
        return BILL_STATUS_REPOSITORY.getDateOrderBill(id);
    }
}
