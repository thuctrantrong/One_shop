package vn.iotstar.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.entity.Cart;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.User;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.service.CartService;
import vn.iotstar.service.ProductService;
import vn.iotstar.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class ResultAPICart {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/add")
    public ModelAndView addProductToCart(
            @RequestParam("userId") int userId,
            @RequestParam("sp_ma") int productId,
            @RequestParam("sp_soluong") int quantity,
            RedirectAttributes redirectAttributes) {

        if (quantity <= 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "Quantity must be greater than zero!");
            return new ModelAndView("redirect:/product-details?id" + productId);
        }

        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found!");
            return new ModelAndView("redirect:/product-details?id" + productId);
        }

        Product product = productOptional.get();

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found!");
            return new ModelAndView("redirect:/product-details?id=" + productId);
        }

        User user = userOptional.get();

        try {
            String result = cartService.addProductToCart(user, product, quantity);
            redirectAttributes.addFlashAttribute("message", "Product added to cart successfully!");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while adding product to cart.");
        }

        return new ModelAndView("redirect:/product-details?id=" + productId);
    }

}

