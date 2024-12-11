package com.lamnguyen.mat_kinh_kimi.service;

import com.lamnguyen.mat_kinh_kimi.repository.impl.ProductDiscountRepositoryImpl;
import com.lamnguyen.mat_kinh_kimi.model.Product;
import com.lamnguyen.mat_kinh_kimi.model.ProductDiscount;

import java.util.List;
import java.util.Map;

public class ProductDiscountService {
    private static ProductDiscountService instance;
    private final ProductDiscountRepositoryImpl PRODUCT_DISCOUNT_REPOSITORY;

    private ProductDiscountService() {
        PRODUCT_DISCOUNT_REPOSITORY = ProductDiscountRepositoryImpl.getInstance();
    }

    public static ProductDiscountService getInstance() {
        return (instance = instance == null ? new ProductDiscountService() : instance);
    }

    public Map<Integer, Double> getPricePercentages(List<Product> products) {
        return PRODUCT_DISCOUNT_REPOSITORY.getPricePercentages(products);
    }

    public Double getPricePercentage(int productId) {
        return PRODUCT_DISCOUNT_REPOSITORY.getPricePercentage(productId);
    }

    public boolean insert(int id, List<ProductDiscount> productDiscounts) {
        boolean result = true;
        for (ProductDiscount productDiscount : productDiscounts) {
            productDiscount.setProductId(id);
            result &= PRODUCT_DISCOUNT_REPOSITORY.insert(productDiscount) == 1;
        }
        return result;
    }

    public List<ProductDiscount> getProductDiscounts(int productId) {
        return PRODUCT_DISCOUNT_REPOSITORY.getProductDiscounts(productId);
    }

    public boolean update(int productId, List<ProductDiscount> productDiscounts) {
        PRODUCT_DISCOUNT_REPOSITORY.removeProductId(productId);
        return insert(productId, productDiscounts);
    }
}
