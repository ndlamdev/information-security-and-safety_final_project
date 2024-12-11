package com.lamnguyen.mat_kinh_kimi.repository.impl;

import com.lamnguyen.mat_kinh_kimi.model.ProductImage;
import com.lamnguyen.mat_kinh_kimi.repository.Repository;

import java.util.List;

public class ProductImageRepositoryImpl extends Repository {
    private static ProductImageRepositoryImpl instance;

    private ProductImageRepositoryImpl() {
    }

    public static ProductImageRepositoryImpl getInstance() {
        return (instance = instance == null ? new ProductImageRepositoryImpl() : instance);
    }

    public List<ProductImage> getProductImagesNonLimit(int productId) {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT pimg.id, pimg.urlImage " +
                                "FROM product_images AS pimg " +
                                "WHERE " +
                                "pimg.productId = ?;")
                        .bind(0, productId)
                        .mapToBean(ProductImage.class)
                        .list()
        );
    }

    public List<ProductImage> getProductImagesLimit(int productId, int limit) {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT id, urlImage " +
                                "FROM product_images AS pimg " +
                                "WHERE " +
                                "productId = ? " +
                                "LIMIT ?;")
                        .bind(0, productId)
                        .bind(1, limit)
                        .mapToBean(ProductImage.class)
                        .list()
        );
    }

    public int insert(ProductImage productImage) {
        String sql = "INSERT INTO product_images(productId, urlImage) values(?, ?);";
        return connector.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind(0, productImage.getProductId())
                        .bind(1, productImage.getUrlImage())
                        .execute()
        );
    }

    public int remove(int productId) {
        return connector.withHandle(handle ->
                handle.createUpdate("DELETE FROM product_images WHERE productid = ?")
                        .bind(0, productId)
                        .execute()
        );
    }
}
