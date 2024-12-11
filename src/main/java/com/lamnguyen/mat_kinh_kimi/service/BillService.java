package com.lamnguyen.mat_kinh_kimi.service;

import com.lamnguyen.mat_kinh_kimi.model.*;
import com.lamnguyen.mat_kinh_kimi.repository.impl.BillRepositoryImpl;
import com.lamnguyen.mat_kinh_kimi.domain.dto.BillManage;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.*;

public class BillService {
    private static BillService service;
    private final Map<String, ProductCart> PRODUCT_CART_MAP;
    private final ProductService PRODUCT_SERVICE;
    private final ModelService MODEL_SERVICE;
    private final BillRepositoryImpl BILL_REPOSITORY;
    private final BillDetailService BILL_DETAIL_SERVICE;
    private final BillStatusService BILL_STATUS_SERVICE;

    public BillService() {
        PRODUCT_CART_MAP = new HashMap<>();
        PRODUCT_SERVICE = ProductService.getInstance();
        MODEL_SERVICE = ModelService.getInstance();
        BILL_REPOSITORY = BillRepositoryImpl.getInstance();
        BILL_DETAIL_SERVICE = BillDetailService.getInstance();
        BILL_STATUS_SERVICE = BillStatusService.getInstance();
    }

    public static BillService getInstance() {
        return (service = service == null ? new BillService() : service);
    }

    public double getTotalBill() {
        double total = 0;
        for (ProductCart product : PRODUCT_CART_MAP.values()) {
            total += product.getPrice() * product.getQuantity();
        }

        return total;
    }

    public double getTotalPriceReduced() {
        double total = 0;
        for (ProductCart product : PRODUCT_CART_MAP.values()) {
            total += product.totalPrice();
        }

        return getTotalBill() - total;
    }

    public void put(String key, ProductCart value) {
        this.PRODUCT_CART_MAP.put(key, value);
    }

    public void remove(String key) {
        this.PRODUCT_CART_MAP.remove(key);
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
                "products=" + PRODUCT_CART_MAP +
                '}';
    }

    public Bill getBill() {
        Bill result = new Bill();
        for (ProductCart productCart : PRODUCT_CART_MAP.values()) {
            BillDetail billDetail = new BillDetail(productCart.getProductId(), productCart.getModel().getId(), productCart.getQuantity(), productCart.getPrice());
            if (productCart.hasDiscount()) billDetail.setPrice(productCart.getDiscount());
            result.addDetail(billDetail);
        }
        return result;
    }

    public boolean saveBill(Bill bill) {
        int id = 0;
//        boolean check = billDetailService.checkQuantity(bill);
        boolean check = true;
        if (check) {
            id = BILL_REPOSITORY.insert(bill);
            BILL_DETAIL_SERVICE.insert(id, bill.getDetails());
            bill.setId(id);
            BillStatus status = new BillStatus(id, "Chờ xác nhận", "Đã xác nhận đơn hàng của bạn", true);
            bill.addStatus(status);
            BILL_STATUS_SERVICE.insert(status);
        }
        return check;
    }

    public ProductCart getProductCart(int productId, int modelId) {
        return PRODUCT_CART_MAP.get(Cart.getKey(productId, modelId));
    }

    public List<Bill> getBillsByUserId(int userId, String status, int offset) {
        List<Bill> billList = BILL_REPOSITORY.getBillsByUserId(userId, status, offset);

        for (Bill bill : billList) {
            try {
                List<BillStatus> billStatuses = BILL_STATUS_SERVICE.getLastStatus(bill.getId());
                bill.setStatuses(billStatuses);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return billList;
    }

    public Bill getBill(int billId) {
        Bill bill = BILL_REPOSITORY.getBill(billId);
        if (bill == null) return null;
        BillStatusService billStatusService = BillStatusService.getInstance();
        BillDetailService billDetailService = BillDetailService.getInstance();
        String addressDetails = AddressService.getInstance().getAddress(bill.getCodeProvince(), bill.getCodeDistrict(), bill.getCodeWard()) +
                "</br>" + bill.getAddress();
        List<BillStatus> billStatuses = billStatusService.getBillStatus(bill.getId());
        List<BillDetail> billDetails = billDetailService.getBillDetails(bill.getId());
        bill.setAddressDetail(addressDetails);
        bill.setStatuses(billStatuses);
        bill.setDetails(billDetails);
        return bill;
    }

    public List<ProductCart> getProductInBill(int billId) {
        Bill bill = BILL_REPOSITORY.getBill(billId);
        if (bill == null) return null;
        List<ProductCart> products = new ArrayList<>();
        int productId, modelId, quantity;
        Model model;

        for (BillDetail billDetail : bill.getDetails()) {
            productId = billDetail.getProductId();
            modelId = billDetail.getModelId();
            quantity = billDetail.getQuantity();
            var productCart = PRODUCT_SERVICE.getProductBill(productId);
            if (productCart == null) {
                continue;
            }
            model = MODEL_SERVICE.getModelForCart(modelId);
            if (model == null) {
                continue;
            }
            productCart.setDiscount(billDetail.getPrice());
            productCart.setQuantity(quantity);
            productCart.setModel(model);
            products.add(productCart);
        }

        return products;
    }

    public boolean updateContact(Bill bill) {
        return BillRepositoryImpl.getInstance().updateContact(bill);
    }

    public List<BillManage> getBillManages(String id, String name, String status, int limit, int offset) {
        List<BillManage> billManages = BillRepositoryImpl.getInstance().getBillManage(id, name, status, limit, offset);
        BillStatusService billStatusService = BillStatusService.getInstance();
        for (BillManage billManager : billManages) {
            int billId = billManager.getBillId();
            billManager.setDate(billStatusService.getDateOrderBill(billId));
        }

        return billManages;
    }

    public int totalBillManage(String id, String name, String status) {
        return BillRepositoryImpl.getInstance().totalBillManage(id, name, status);
    }
}
