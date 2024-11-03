package model.bean;


import java.util.StringTokenizer;

public class ProductImage {
    private Integer id, productId;
    private String urlImage;

    public ProductImage() {
    }

    public ProductImage(String data) {
        StringTokenizer tk = new StringTokenizer(data, ",");
        this.urlImage = data;
    }

    @Override
    public String toString() {
        return "ProductImage{" +
                "productId=" + productId +
                ", urlImage='" + urlImage + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
