package com.lamnguyen.mat_kinh_kimi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
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

    public void parseProductImages(String[] dataProductImages) {
        this.productImages = this.productImages == null ? new ArrayList<>() : this.productImages;
        for (String dataProductImage : dataProductImages) {
            this.productImages.add(new ProductImage(dataProductImage));
        }
    }

    public boolean hasDiscount() {
        return Double.compare(this.discount, 0) != 0;
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
        productDiscounts = new ArrayList<>();
        if (parameterValues == null) return;
        for (String productDiscountStr : parameterValues) {
            ProductDiscount productDiscount = new ProductDiscount(productDiscountStr);
            this.productDiscounts.add(productDiscount);
        }
    }

    public void setModel(Model model) {
        this.models = this.models == null ? new ArrayList<>() : this.models;
        this.models.add(model);
    }

    public boolean isLock() {
        return delete != 0;
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
