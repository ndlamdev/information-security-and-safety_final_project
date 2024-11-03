package model.service;

import model.bean.*;

import java.util.List;

public class CartService {
    private Cart cart;

    public CartService(Cart cart) {
        this.cart = cart;
    }

    public CartService() {
        cart = new Cart();
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<ProductCart> getAllProductCart() {
        return cart.getAllProductCart();
    }

    public boolean addProductCart(int productId, int modelId, int quantity) {
        return cart.addProduct(productId, modelId, quantity);
    }

    public void removeProductCart(int productId, int modelId) {
        cart.removeProduct(productId, modelId);
    }

    public boolean increaseProductCart(int productId, int modelId, int quantity) {
        return cart.increase(productId, modelId, quantity);
    }

    public boolean reduceProductCart(int productId, int modelId, int quantity) {
        return cart.reduce(productId, modelId, quantity);
    }

    public int getTotalProduct() {
        return cart.totalProductCart();
    }

    public double getTotalPriceProducts(int productId, int modelId) {
        return this.cart.getTotalPriceProduct(productId, modelId);
    }

    public int getQuantity(int productId, int modelId) {
        return this.cart.getQuantity(productId, modelId);
    }

    public double totalPrice() {
        double totalPrice = 0;
        for(ProductCart p : this.cart.getAllProductCart()){
            totalPrice += p.totalPrice();
        }

        return totalPrice;
    }

    public ProductCart getProductCart(int productId, int modelId) {
        return this.cart.getProductCart(productId, modelId);
    }

    public void bought(Bill bill){
        cart.bought(bill);
    }


    public static double getTotalPriceProducts(List<ProductCart> productCarts) {
        return productCarts.stream().mapToDouble(ProductCart::totalPrice).sum();
    }
}
