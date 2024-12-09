package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.User;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "/views/homepage";
    }

    @GetMapping("/admin")
    public String homeadmin() {
        return "/admin/adminuser";
    }
    @GetMapping("/shop")
    public String homeadminshop() {
        return "/admin/shop";
    }
    @GetMapping("/product")
    public String homeadminproduct() {
        return "/admin/product";
    }
    @GetMapping("/userproduct")
    public String homeuserproduct() {
        return "/user/product";
    }
    @GetMapping("/user")
    public String userhomepage() {
        return "/user/userhomepage.html";
    }
    @GetMapping("/product-details")
    public String productdetails() {
        return "/user/product-details.html";
    }
}
