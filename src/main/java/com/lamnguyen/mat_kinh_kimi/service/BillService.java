package com.lamnguyen.mat_kinh_kimi.service;

import com.lamnguyen.mat_kinh_kimi.domain.dto.Signature;
import com.lamnguyen.mat_kinh_kimi.domain.dto.BillWillDelete;
import com.lamnguyen.mat_kinh_kimi.model.*;
import com.lamnguyen.mat_kinh_kimi.repository.impl.BillRepositoryImpl;
import com.lamnguyen.mat_kinh_kimi.domain.dto.BillManage;
import com.lamnguyen.mat_kinh_kimi.util.enums.BillStatusEnum;
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

    public int saveBill(Bill bill) {
        int id;
        bill.setVerify(false);
        id = BILL_REPOSITORY.insert(bill);
        bill.setId(id);
        BILL_DETAIL_SERVICE.insert(id, bill.getDetails());
        BillStatus status = new BillStatus(id, BillStatusEnum.NOT_SIGN.getStatus(), "Đơn hàng chưa được ký", true);
        bill.addStatus(status);
        BILL_STATUS_SERVICE.insert(status);
        return id;
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
        bill.setAddressDetail(AddressService.getInstance().getAddress(bill.getCodeProvince(), bill.getCodeDistrict(), bill.getCodeWard()) + "</br>" + bill.getAddress());
        bill.setStatuses(BILL_STATUS_SERVICE.getBillStatus(billId));
        bill.setDetails(BILL_DETAIL_SERVICE.getBillDetails(billId));
        return bill;
    }

    public List<ProductCart> getProductInBill(int billId) {
        if (!BILL_REPOSITORY.exists(billId)) throw new NullPointerException();
        List<ProductCart> products = new ArrayList<>();

        for (BillDetail billDetail : BILL_DETAIL_SERVICE.getBillDetails(billId)) {
            var productId = billDetail.getProductId();
            var modelId = billDetail.getModelId();
            var quantity = billDetail.getQuantity();
            var productCart = PRODUCT_SERVICE.getProductBill(productId);
            if (productCart == null) {
                continue;
            }
            var model = MODEL_SERVICE.getModelForCart(modelId);
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

    public int updateContact(Bill bill) {
        return BILL_REPOSITORY.updateContact(bill);
    }

    public List<BillManage> getBillManages(String id, String name, String status, int limit, int offset) {
        List<BillManage> billManages = BILL_REPOSITORY.getBillManage(id, name, status, limit, offset);
        for (BillManage billManager : billManages) {
            int billId = billManager.getBillId();
            billManager.setDate(BILL_STATUS_SERVICE.getDateOrderBill(billId));
        }

        return billManages;
    }

    public int totalBillManage(String id, String name, String status) {
        return BILL_REPOSITORY.totalBillManage(id, name, status);
    }

    public List<BillWillDelete> getBillsWillDelete(Integer userId){
        return BILL_REPOSITORY.getBillsWillDelete(userId);
    }

    public int updateSignature(Integer id, Signature signature) {
        return BILL_REPOSITORY.updateSignature(id, signature);
    }

    public Signature findSignature(Integer id) {
        return BILL_REPOSITORY.findSignature(id);
    }

    public boolean isVerify(int billId) {
        return BILL_REPOSITORY.isVerify(billId);
    }

    public void updateVerify(int billId, boolean verify) {
        BILL_REPOSITORY.updateVerify(billId, verify);
    }
}
