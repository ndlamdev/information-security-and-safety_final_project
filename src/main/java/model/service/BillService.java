package model.service;

import model.DAO.BillDAO;
import model.bean.*;
import model.dto.BillManage;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BillService {
    private static BillService service;
    private Map<String, ProductCart> productCartMap;

    public BillService() {
        productCartMap = new HashMap<>();
    }

    public static BillService getInstance() {
        return (service = service == null ? new BillService() : service);
    }

    public double getTotalBill() {
        double total = 0;
        for (ProductCart product : productCartMap.values()) {
            total += product.getPrice() * product.getQuantity();
        }

        return total;
    }

    public double getTotalPriceReduced() {
        double total = 0;
        for (ProductCart product : productCartMap.values()) {
            total += product.totalPrice();
        }

        return getTotalBill() - total;
    }

    public void put(String key, ProductCart value) {
        this.productCartMap.put(key, value);
    }

    public void remove(String key) {
        this.productCartMap.remove(key);
    }

    public void setUpJSON(JSONObject json) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("vi", "VN"));
        double totalBill = this.getTotalBill();
        double totalPriceReduced = this.getTotalPriceReduced();
        double shippingFee = Double.compare(totalBill, 0) == 0 ? 0 : 20000;
        json.put("totalBill", nf.format(totalBill));
        json.put("totalPriceReduced", nf.format(totalPriceReduced));
        json.put("shippingFee", nf.format(shippingFee));
        json.put("totalPay", nf.format(totalBill - totalPriceReduced + shippingFee));
    }

    @Override
    public String toString() {
        return "BillService{" +
                "products=" + productCartMap +
                '}';
    }

    public Bill getBill() {
        Bill result = new Bill();
        for (ProductCart productCart : productCartMap.values()) {
            BillDetail billDetail = new BillDetail(productCart.getProductId(), productCart.getModel().getId(), productCart.getQuantity(), productCart.getPrice());
            if (productCart.hasDiscount()) billDetail.setPrice(productCart.getDiscount());
            result.addDetail(billDetail);
        }
        return result;
    }

    public boolean saveBill(Bill bill) {
        BillDAO billDAO = new BillDAO();
        BillDetailService billDetailService = new BillDetailService();
        BillStatusService billStatusService = new BillStatusService();
        int id = 0;
//        boolean check = billDetailService.checkQuantity(bill);
        boolean check = true;
        if (check) {
            id = billDAO.insert(bill);
            billDetailService.insert(id, bill.getDetails());
            bill.setId(id);
            BillStatus status = new BillStatus(id, "Chờ xác nhận", "Đã xác nhận đơn hàng của bạn", true);
            bill.addStatus(status);
            billStatusService.insert(status);
        }
        return check;
    }

    public boolean bought(int userId, int productId, int modelId) {
        return new BillDAO().bought(userId, productId, modelId);
    }

    public ProductCart getProductCart(int productId, int modelId) {
        return productCartMap.get(Cart.getKey(productId, modelId));
    }

    public List<Bill> getBillsByUserId(int userId, String status, int offset) {
        List<Bill> billList = new BillDAO().getBillsByUserId(userId, status, offset);
        BillStatusService billStatusService = BillStatusService.getInstance();
        for (Bill bill : billList) {
            try {
                List<BillStatus> billStatuses = billStatusService.getLastStatus(bill.getId());
                bill.setStatuses(billStatuses);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return billList;
    }

    public Bill getBill(int billId) {
        Bill bill = new BillDAO().getBill(billId);
        if (bill == null) return null;
        BillStatusService billStatusService = BillStatusService.getInstance();
        BillDetailService billDetailService = BillDetailService.getInstance();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String addressDetails = AddressService.getInstance().getAddress(bill.getCodeProvince(), bill.getCodeDistrict(), bill.getCodeWard()) +
                "</br>" + bill.getAddress();
        List<BillStatus> billStatuses = billStatusService.getBillStatus(bill.getId());
        List<BillDetail> billDetails = billDetailService.getBillDetails(bill.getId());
        bill.setAddressDetail(addressDetails);
        bill.setStatuses(billStatuses);
        bill.setDetails(billDetails);
        return bill;
    }

    public boolean updateContact(Bill bill) {
        return BillDAO.getInstance().updateContact(bill);
    }

    public List<BillManage> getBillManages(String id, String name, String status, int limit, int offset) {
        List<BillManage> billManages = BillDAO.getInstance().getBillManage(id, name, status, limit, offset);
        BillStatusService billStatusService = BillStatusService.getInstance();
        for (BillManage billManager : billManages) {
            int billId = billManager.getBillId();
            billManager.setDate(billStatusService.getDateOrderBill(billId));
        }

        return billManages;
    }

    public int totalBillManage(String id, String name, String status) {
        return BillDAO.getInstance().totalBillManage(id, name, status);
    }
}
