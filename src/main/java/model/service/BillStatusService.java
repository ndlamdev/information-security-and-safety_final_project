package model.service;

import model.DAO.BillStatusDAO;
import model.bean.BillStatus;

import java.time.LocalDateTime;
import java.util.List;

public class BillStatusService {
    private static BillStatusService service;
    public static BillStatusService getInstance() {
        return (service = service == null ? new BillStatusService() : service);
    }

    public void insert(BillStatus status) {
        BillStatusDAO billStatusDAO = new BillStatusDAO();
        billStatusDAO.insert(status);
    }
    public BillStatus getInfDateStatus(int billId){
        return new BillStatusDAO().getInfDateStatus(billId);
    }

    public List<BillStatus> getLastStatus(Integer id) {
        return new BillStatusDAO().getLastBillStatus(id);
    }

    public List<BillStatus> getBillStatus(Integer id) {
        return new BillStatusDAO().getBillStatus(id);
    }

    public LocalDateTime getDateOrderBill(Integer id) {
        return new BillStatusDAO().getDateOrderBill(id);
    }
}
