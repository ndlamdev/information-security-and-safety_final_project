package model.DAO;

import model.bean.BillDetail;
import model.bean.Product;
import model.service.BillDetailService;
import model.service.ModelService;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.util.*;

public class BillDetailDAO extends DAO {
    private static BillDetailDAO instance;

    public static BillDetailDAO getInstance() {
        return instance == null ? new BillDetailDAO() : instance;
    }

    public int getTotalQuantitySold(Integer productId) {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT sum(bd.quantity) " +
                                "FROM bill_details AS bd " +
                                "WHERE bd.productId = ?;")
                        .bind(0, productId)
                        .mapTo(Integer.class)
                        .findFirst().orElse(0)
        );
    }

    public void insert(BillDetail detail) {
        connector.withHandle(handle ->
                handle.createUpdate("INSERT INTO bill_details(billId, productId, modelId, quantity, price) " +
                                "VALUES(?, ?, ?, ?, ?);")
                        .bind(0, detail.getBillId())
                        .bind(1, detail.getProductId())
                        .bind(2, detail.getModelId())
                        .bind(3, detail.getQuantity())
                        .bind(4, detail.getPrice())
                        .execute()
        );
    }

//    public boolean checkQuantity(ArrayList<BillDetail> details) {
//        for (BillDetail detail : details) {
//            int quantity = 0, totalSale = 0;
//            ModelService modelService = ModelService.getInstance();
//            BillDetailService billDetailService = new BillDetailService();
//            quantity = modelService.getQuantity(detail.getModelId());
//            totalSale = billDetailService.getTotalSale(detail.getModelId());
//            if((quantity - totalSale - detail.getQuantity()) < 0) return false;
//        }
//        return true;
//    }

    public int getTotalSale(Integer billId) {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT SUM(bd.quantity) AS sum_quantity " +
                                "FROM bill_details AS bd " +
                                "WHERE bd.modelId = ?")
                        .bind(0, billId)
                        .mapTo(Integer.class)
                        .findFirst().orElse(0)
        );
    }

    public List<BillDetail> getBillDetails(Integer billId) {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT productId, modelId, quantity, price FROM bill_details WHERE billId = :billId")
                        .bind("billId", billId)
                        .mapToBean(BillDetail.class)
                        .list()

        );
    }

    private void insertSampleData() {
        Random rd = new Random(System.currentTimeMillis());
        for (int i = 1; i <= 100; i++) {
            int id = i;
            int billId = rd.nextInt(1, 101);
            int productId = rd.nextInt(1, 373);
            int modelId = productId;
            int quantity = rd.nextInt(1, 11);
            double price = rd.nextDouble(100000, 10000000);
            connector.withHandle(handle ->
                    handle.createUpdate("INSERT INTO `mat_kinh_kimi`.`bill_details` (`id`, `billId`, `productId`, `modelId`, `quantity`, `price`) VALUES (?, ?, ?, ?, ?, ?);")
                            .bind(0, id)
                            .bind(1, billId)
                            .bind(2, productId)
                            .bind(3, modelId)
                            .bind(4, quantity)
                            .bind(5, price)
                            .execute()
            );
        }
    }

    public static void main(String[] args) {
        BillDetailDAO billDetailDAO = new BillDetailDAO();
        billDetailDAO.insertSampleData();
    }
}
