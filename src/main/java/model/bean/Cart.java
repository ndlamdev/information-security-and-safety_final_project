package model.bean;

import model.service.ModelService;
import model.service.ProductDiscountService;
import model.service.ProductService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<String, ProductCart> cart;

    public Cart() {
        cart = new HashMap<String, ProductCart>();
    }

    public Map<String, ProductCart> getCart() {
        return cart;
    }

    public static String getKey(int productId, int modelId) {
        return productId + "-" + modelId;
    }

    public boolean addProduct(int productId, int modelId, int quantity) {
        String key = getKey(productId, modelId);
        ProductCart productCart = cart.get(key);
        if (productCart != null) return productCart.increase(quantity);

        ProductService service = ProductService.getInstance();
        ModelService modelService = ModelService.getInstance();
        ProductDiscountService productDiscountService = ProductDiscountService.getInstance();

        productCart = service.getProductCart(productId);
        if (productCart == null) return false;
        Model model = modelService.getModelForCart(modelId);
        if (model == null) return false;
        Double productDiscount = productDiscountService.getPricePercentage(productId);
        productDiscount = Double.compare(productDiscount, 0.0) != 0 ? (1.0 - productDiscount) * productCart.getPrice() : 0.0;
        productCart.setDiscount(productDiscount);
        productCart.setQuantity(quantity);
        productCart.setModel(model);

        cart.put(key, productCart);
        return true;
    }

    public void removeProduct(int productId, int modelId) {
        String key = getKey(productId, modelId);
        cart.remove(key);
    }

    public boolean increase(int productId, int modelId, int quantity) {
        String key = getKey(productId, modelId);
        if (!cart.containsKey(key)) return false;
        return cart.get(key).increase(quantity);
    }

    public boolean reduce(int productId, int modelId, int quantity) {
        String key = getKey(productId, modelId);
        if (!cart.containsKey(key)) return false;
        return cart.get(key).reduce(quantity);
    }

    public List<ProductCart> getAllProductCart() {
        return cart.values().stream().toList();
    }

    public int totalProductCart() {
        return cart.size();
    }

    public double getTotalPriceProduct(int productId, int modelId) {
        return this.cart.get(getKey(productId, modelId)).totalPrice();
    }

    public int getQuantity(int productId, int modelId) {
        return this.cart.get(getKey(productId, modelId)).getQuantity();
    }

    public ProductCart getProductCart(int productId, int modelId) {
        return this.cart.get(getKey(productId, modelId));
    }

    public void bought(Bill bill) {
        List<BillDetail> billDetails = bill.getDetails();
        for (BillDetail billDetail : billDetails) {
            String key = Cart.getKey(billDetail.getProductId(), billDetail.getModelId());
            cart.remove(key);
        }
    }
}
