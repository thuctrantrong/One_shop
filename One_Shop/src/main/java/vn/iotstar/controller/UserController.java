package vn.iotstar.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.entity.Cart;
import vn.iotstar.entity.OrderRequest;
import vn.iotstar.entity.ProductOrder;
import vn.iotstar.entity.User;
import vn.iotstar.service.CartService;
import vn.iotstar.service.OrderService;
import vn.iotstar.service.UserService;
import vn.iotstar.util.CommonUtil;
import vn.iotstar.util.OrderStatus;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CommonUtil commonUtil;


    @GetMapping("/cartQuantityUpdate")
    public String updateCartQuantity(@RequestParam String sy, @RequestParam Integer cid, HttpSession session) {
        cartService.updateQuantity(sy, cid, session);

        String errorMsg = (String) session.getAttribute("errorMsg");
        if (errorMsg != null) {
            return "redirect:/user/cart";
        }

        return "redirect:/user/cart";
    }

    public User getLoggedInUserDetails(Principal p) {
        String email = p.getName();
        User userDtls = userService.getUserByEmail(email);

        return userDtls;
    }

    @PostMapping("/save-order")
    public String savePage(@ModelAttribute OrderRequest request, Principal p) throws Exception {
        // System.out.println(request);

        User user = getLoggedInUserDetails(p);
        orderService.saveOrder(user.getUserID(), request);
        return "redirect:/user/success";
    }

    @GetMapping("/success")
    public String loadSuccess() {
        return "/user/success";
    }

    @GetMapping("/orders")
    public String orderPage(Principal p, Model m) {
        User user = getLoggedInUserDetails(p);
        List<Cart> carts = cartService.getCartsByUser(user.getUserID());
        if (carts.isEmpty()) {
            m.addAttribute("errorMsg", "You must select at least one product to proceed with payment.");
            return "redirect:/user/cart";
        }

        m.addAttribute("carts", carts);
        if (carts.size() > 0) {
            Double orderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
            Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice() + 5 + 10;
            m.addAttribute("orderPrice", orderPrice);
            m.addAttribute("totalOrderPrice", totalOrderPrice);
        }
        return "/user/order";
    }

    @GetMapping("/user-orders")
    public String myOrder(Model m, Principal p) {
        User loginUser = getLoggedInUserDetails(p);
        List<ProductOrder> orders = orderService.getOrderByUser(loginUser.getUserID());
        m.addAttribute("orders", orders);
        return "/user/my_orders";
    }

    @GetMapping("/update-status")
    public String updateOrderStatus(@RequestParam Integer id, @RequestParam Integer st, HttpSession session) {

        OrderStatus[] values = OrderStatus.values();
        String status = null;

        for (OrderStatus orderSt : values) {
            if (orderSt.getId().equals(st)) {
                status = orderSt.getName();
            }
        }

        ProductOrder updateOrder = orderService.updateOrderStatus(id, status);
        try {
            commonUtil.sendMailForProductOrder(Collections.singletonList(updateOrder), status);
        } catch (Exception e) {

            e.printStackTrace();
        }

        if (!ObjectUtils.isEmpty(updateOrder)) {
            session.setAttribute("succMsg", "Status updated!");
        } else {
            session.setAttribute("errorMsg", "Status not updated!");
        }

        return "redirect:/user/user-orders";
    }










}
