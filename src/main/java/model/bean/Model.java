package model.bean;

import java.util.Objects;
import java.util.StringTokenizer;

public class Model {
    private Integer id, productId, quantity, totalQuantitySold;
    private String name, urlImage;

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", totalQuantitySold=" + getTotalQuantitySold() +
                ", name='" + name + '\'' +
                ", urlImage='" + urlImage + '\'' +
                '}';
    }

    public Model(String data) {
        StringTokenizer tk = new StringTokenizer(data, ",");
        this.name = tk.nextToken();
        this.quantity = Integer.parseInt(tk.nextToken());
        this.urlImage = tk.nextToken();
    }

    public Model() {
    }

    public Integer getTotalQuantitySold() {
        return totalQuantitySold == null ? 0 : totalQuantitySold;
    }

    public void setTotalQuantitySold(Integer totalQuantitySold) {
        this.totalQuantitySold = totalQuantitySold;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Objects.equals(id, model.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, name, urlImage);
    }

    public boolean available() {
        return this.quantity > this.getTotalQuantitySold();
    }
}
