package model.bean;

public class ProductReview {
    private Integer productId, modelId, quantity;
    private double price;
    private String productName, modelName, urlImage;

    public ProductReview() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    @Override
    public String toString() {
        return "ProductReview{" +
                "productId=" + productId +
                ", modelId=" + modelId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", productName='" + productName + '\'' +
                ", modelName='" + modelName + '\'' +
                ", urlImage='" + urlImage + '\'' +
                '}';
    }
}
