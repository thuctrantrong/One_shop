package vn.iotstar.service.impl;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.Cart;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.User;
import vn.iotstar.repository.CartRepository;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.service.CartService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public String addProductToCart(User user, Product product, int quantity) {

        if (product == null) {
            return "Sản phẩm không tồn tại!";
        }

        if (product.getStock() < quantity) {
            return "Không đủ số lượng trong kho!";
        }

        List<Cart> existingCart = cartRepository.findByUser_UserID(user.getUserID());
        if (existingCart.isEmpty()) {
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setCreatedAt(LocalDateTime.now());
            cartRepository.save(newCart);
        }

        List<Cart> userCart = cartRepository.findByUser_UserIDAndProduct_ProductId(user.getUserID(), product.getProductId());
        if (!userCart.isEmpty()) {
            Cart cart = userCart.get(0);
            cart.setQuantity(cart.getQuantity() + quantity);
            cartRepository.save(cart);
        } else {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setQuantity(quantity);
            cart.setCreatedAt(LocalDateTime.now());
            cartRepository.save(cart);
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        return "Sản phẩm đã được thêm vào giỏ hàng!";

    }

    @Override
    public List<Cart> getCartsByUser(int userId) {
        List<Cart> carts = cartRepository.findByUser_UserID(userId);

        Double totalOrderPrice = 0.0;
        List<Cart> updateCarts = new ArrayList<>();
        for (Cart c : carts) {
            // Kiểm tra nếu product là null
            if (c.getProduct() != null) {
                Double totalPrice = c.getProduct().getPrice() * c.getQuantity();
                c.setTotalPrice(totalPrice);

                totalOrderPrice += totalPrice;
                c.setTotalOrderPrice(totalOrderPrice);
                updateCarts.add(c);
            } else {
                // Ghi log hoặc xử lý nếu product là null
                System.out.println("Product is null for Cart ID: " + c.getCartId());
            }
        }

        return updateCarts;
    }


    @Override
    public void updateQuantity(String sy, Integer cid, HttpSession session) {
        Cart cart = cartRepository.findById(cid).get();
        Product product = cart.getProduct();
        int updateQuantity;

        int stock = product.getStock();

        if (sy.equalsIgnoreCase("de")) {
            updateQuantity = cart.getQuantity() - 1;

            if (updateQuantity <= 0) {
                cartRepository.delete(cart);
            } else {
                cart.setQuantity(updateQuantity);
                cartRepository.save(cart);
            }
        } else {
            updateQuantity = cart.getQuantity() + 1;

            if (updateQuantity > stock) {
                session.setAttribute("errorMsg", "Cannot add more than the quantity of products available in stock.");
                return;
            }

            cart.setQuantity(updateQuantity);
            cartRepository.save(cart);
        }

    }


}
