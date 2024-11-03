package model.service;

import model.DAO.ProductDiscountDAO;
import model.bean.Product;
import model.bean.ProductDiscount;

import java.util.List;
import java.util.Map;

public class ProductDiscountService {
    private static ProductDiscountService instance;

    public static ProductDiscountService getInstance() {
        return instance == null ? new ProductDiscountService() : instance;
    }

    public Map<Integer, Double> getPricePercentages(List<Product> products) {
        ProductDiscountDAO productDiscountDAO = ProductDiscountDAO.getInstance();
        return productDiscountDAO.getPricePercentages(products);
    }

    public Double getPricePercentage(int productId) {
        ProductDiscountDAO productDiscountDAO = ProductDiscountDAO.getInstance();
        return productDiscountDAO.getPricePercentage(productId);
    }

    public boolean insert(int id, List<ProductDiscount> productDiscounts) {
        boolean result = true;
        for (ProductDiscount productDiscount : productDiscounts) {
            productDiscount.setProductId(id);
            result &= ProductDiscountDAO.getInstance().insert(productDiscount) == 1 ? true : false;
        }
        return result;
    }

    public List<ProductDiscount> getProductDiscounts(int productId) {
        return ProductDiscountDAO.getInstance().getProductDiscounts(productId);
    }

    public boolean update(int productId, List<ProductDiscount> productDiscounts) {
        ProductDiscountDAO.getInstance().removeProductId(productId);
        return insert(productId, productDiscounts);
    }
}
