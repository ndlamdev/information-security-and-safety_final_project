package model.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.StringTemplate.STR;

public class Product {
    private Integer id, categoryId, starNumber, totalReview, totalQuantitySold, delete;
    private String name, brandName, describe, material, type, categoryName;
    private Double price, discount;
    private List<Model> models;
    private List<Review> reviews;
    private List<ProductImage> productImages;
    private List<ProductDiscount> productDiscounts;

    public Product() {
        name = "";
        brandName = "";
        material = "";
        type = "";
        categoryName = "";
        price = 0.0;
        models = new ArrayList<>();
        reviews = new ArrayList<>();
        productImages = new ArrayList<>();
        productDiscounts = new ArrayList<>();
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    public List<Model> getModels() {
        return models;
    }

    public void parseModels(List<Model> models) {
        this.models = models;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void parseProductImages(String[] dataProductImages) {
        this.productImages = this.productImages == null ? new ArrayList<>() : this.productImages;
        for (String dataProductImage : dataProductImages) {
            this.productImages.add(new ProductImage(dataProductImage));
        }
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public Integer getStarNumber() {
        return starNumber;
    }

    public void setStarNumber(Integer starNumber) {
        this.starNumber = starNumber;
    }

    public boolean hasDiscount() {
        return Double.compare(this.discount, 0) != 0;
    }

    public Integer getTotalReview() {
        return totalReview;
    }

    public void setTotalReview(Integer totalReview) {
        this.totalReview = totalReview;
    }

    public Integer getTotalQuantitySold() {
        return totalQuantitySold;
    }

    public void setTotalQuantitySold(Integer totalQuantitySold) {
        this.totalQuantitySold = totalQuantitySold;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }


    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public boolean available() {
        for (Model model : models) {
            if (model.available()) return true;
        }
        return false;
    }


    public void parseModels(String[] models) {
        this.models = this.models == null ? new ArrayList<>() : this.models;
        for (String data : models) {
            Model model = new Model(data);
            this.models.add(model);
        }
    }

    public void parseProductDiscounts(String[] parameterValues) {
        this.productDiscounts = this.productDiscounts == null ? new ArrayList<>() : this.productDiscounts;
        productDiscounts = new ArrayList<>();
        if (parameterValues == null) return;
        for (String productDiscountStr : parameterValues) {
            ProductDiscount productDiscount = new ProductDiscount(productDiscountStr);
            this.productDiscounts.add(productDiscount);
        }
    }

    public List<ProductDiscount> getProductDiscounts() {
        return productDiscounts;
    }

    public void parseProductDiscounts(List<ProductDiscount> productDiscounts) {
        this.productDiscounts = productDiscounts;
    }

    public void setModel(Model model) {
        this.models = this.models == null ? new ArrayList<>() : this.models;
        this.models.add(model);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", starNumber=" + starNumber +
                ", totalReview=" + totalReview +
                ", totalQuantitySold=" + totalQuantitySold +
                ", name='" + name + '\'' +
                ", brandName='" + brandName + '\'' +
                ", describe='" + describe + '\'' +
                ", material='" + material + '\'' +
                ", type='" + type + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", models=" + models +
                ", reviews=" + reviews +
                ", productImages=" + productImages +
                ", productDiscounts=" + productDiscounts +
                '}';
    }

    public boolean isLock() {
        return delete == 0 ? false : true;
    }

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public void setProductDiscounts(List<ProductDiscount> productDiscounts) {
        this.productDiscounts = productDiscounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
