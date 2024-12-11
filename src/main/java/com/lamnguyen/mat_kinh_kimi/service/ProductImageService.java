package com.lamnguyen.mat_kinh_kimi.service;

import com.lamnguyen.mat_kinh_kimi.repository.impl.ProductImageRepositoryImpl;
import com.lamnguyen.mat_kinh_kimi.model.ProductImage;

import java.util.List;

public class ProductImageService {
    private static ProductImageService instance;
    private final ProductImageRepositoryImpl PRODUCT_IMAGE_REPOSITORY;

    private ProductImageService() {
        PRODUCT_IMAGE_REPOSITORY = ProductImageRepositoryImpl.getInstance();
    }

    public static ProductImageService getInstance() {
        return (instance = instance == null ? new ProductImageService() : instance);
    }

    public List<ProductImage> getProductImage(int productId, int limit) {
        if (limit == 0) {
            return PRODUCT_IMAGE_REPOSITORY.getProductImagesNonLimit(productId);
        }

        return PRODUCT_IMAGE_REPOSITORY.getProductImagesLimit(productId, limit);
    }

    public boolean insert(int productId, List<ProductImage> productImages) {
        boolean result = true;
        for (ProductImage productImage : productImages) {
            productImage.setProductId(productId);
            result &= PRODUCT_IMAGE_REPOSITORY.insert(productImage) == 1;
        }

        return result;
    }

    public boolean update(int productId, List<ProductImage> productImages) {
        PRODUCT_IMAGE_REPOSITORY.remove(productId);
        return insert(productId, productImages);
    }
}
