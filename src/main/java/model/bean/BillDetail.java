package model.bean;

public class BillDetail {
    private Integer billId, productId, modelId, quantity;
    private Double price;

    public BillDetail() {
    }

    public BillDetail(Integer productId, Integer modelId, Integer quantity, Double price) {
        this.productId = productId;
        this.modelId = modelId;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "BillDetailDAO{" +
                "billId=" + billId +
                ", productId=" + productId +
                ", modelId=" + modelId +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
