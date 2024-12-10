package vn.iotstar.service;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Cart;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.User;

import java.util.List;

public interface CartService {
    public String addProductToCart(User user, Product product, int quantity);

    public List<Cart> getCartsByUser(int userId);


    public void updateQuantity(String sy, Integer cid, HttpSession session);
}
