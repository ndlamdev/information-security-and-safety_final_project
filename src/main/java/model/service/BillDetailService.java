package model.service;

import model.DAO.BillDetailDAO;
import model.bean.Bill;
import model.bean.BillDetail;
import model.bean.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BillDetailService {
    private static BillDetailService service;

    public static BillDetailService getInstance() {
        return (service = service == null ? new BillDetailService() : service);
    }

    public int getTotalQuantitySold(Integer productId) {
        BillDetailDAO billDetailDAO = BillDetailDAO.getInstance();
        return billDetailDAO.getTotalQuantitySold(productId);
    }

    public void insert(int id, List<BillDetail> details) {
        BillDetailDAO billDetailDAO = BillDetailDAO.getInstance();
        for(BillDetail billDetail : details) {
            billDetail.setBillId(id);
            billDetailDAO.insert(billDetail);
        }
    }

//    public boolean checkQuantity(Bill bill) {
//        BillDetailDAO billDetailDAO = BillDetailDAO.getInstance();
//        return billDetailDAO.checkQuantity(bill.getDetails());
//    }

    public int getTotalSale(Integer billId) {
        BillDetailDAO billDetailDAO = BillDetailDAO.getInstance();
        return billDetailDAO.getTotalSale(billId);
    }

    public List<BillDetail> getBillDetails(Integer billId) {
        BillDetailDAO billDetailDAO = BillDetailDAO.getInstance();
        return billDetailDAO.getBillDetails(billId);
    }
}
