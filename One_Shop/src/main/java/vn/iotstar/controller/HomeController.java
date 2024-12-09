package vn.iotstar.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Promotion;
import vn.iotstar.entity.User;
import vn.iotstar.service.PromotionService;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.util.CommonUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private CommonUtil commonUtil;
    @GetMapping("/")
    public String home() {
        return "/homepage";
    }

    @GetMapping("/admin")
    public String homeadmin() {
        return "/admin/adminuser";
    }
    @GetMapping("/admin/shop")
    public String homeadminshop() {
        return "/admin/shop";
    }
    @GetMapping("/admin/product")
    public String homeadminproduct() {
        return "/admin/product";
    }
    @GetMapping("/admin/category")
    public String homeadmincategory() {
        return "/admin/category";
    }
    @GetMapping("/userproduct")
    public String homeuserproduct() {
        return "/user/product";
    }
    @GetMapping("/product-details")
    public String productdetails() {
        return "/user/product-details.html";
    }
    @GetMapping("/biopage")
    public String bio(HttpSession session, Model model) {
        String userEmail = (String) session.getAttribute("useremail");
        User user = userService.findUserByEmail(userEmail);
        model.addAttribute("user", user);

        return "/views/Biopage";
    }
    @PostMapping("/role/edituser")
    public String editUser(@ModelAttribute User user, HttpSession session, Model model) {
        String userEmail = (String) session.getAttribute("useremail");
        User existingUser = userService.findUserByEmail(userEmail);
        existingUser.setUsername(user.getUsername());
        existingUser.setFullName(user.getFullName());
        existingUser.setAddress(user.getAddress());

        userService.updateUser(existingUser);
        model.addAttribute("message", "Profile updated successfully.");
        model.addAttribute("user", existingUser);

        return "/views/Biopage";
    }
    @PostMapping("/role/changepass")
    public String changePassUser(@RequestParam("password") String oldPassword,
                                 @RequestParam("password1") String newPassword,
                                 @RequestParam("password2") String confirmPassword,
                                 HttpSession session, Model model) {
        String userEmail = (String) session.getAttribute("useremail");
        User existingUser = userService.findUserByEmail(userEmail);
        if (!passwordEncoder.matches(oldPassword, existingUser.getPasswordHash())) {
            User user = userService.findUserByEmail(userEmail);
            model.addAttribute("user", user);
            model.addAttribute("messagep", "Mật khẩu cũ không đúng.");
            return "views/Biopage";
        }
        if (!newPassword.equals(confirmPassword)) {
            User user = userService.findUserByEmail(userEmail);
            model.addAttribute("user", user);
            model.addAttribute("messagep", "Mật khẩu mới và xác nhận mật khẩu không khớp.");
            return "views/Biopage";
        }
        userService.changepassUser(userEmail,newPassword);
        model.addAttribute("messagep", "change pass successfully.");
        model.addAttribute("user", existingUser);
        return "views/Biopage";
    }
    @GetMapping("/admin/promotion")
    public String showPromotion() {
        return "/admin/adminpromotion";
    }
    @GetMapping("/user")
    public String homeuser() {
        return "user/home";
    }

    @GetMapping("/signin")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, HttpSession session, @RequestParam String confirmpassword)
            throws IOException {

        Boolean existsEmail = userService.existsEmail(user.getEmail());
        if (existsEmail) {
            session.setAttribute("errorMsg", "Email already exists!");
            return "redirect:/register"; //
        }

        if (!user.getPasswordHash().equals(confirmpassword)) {
            session.setAttribute("errorMsg", "Passwords do not match!");
            return "redirect:/register";
        }

        User savedUser = userService.saveUser(user);

        session.setAttribute("succMsg", "Registration successful!");
        return "redirect:/register";
    }
    // Forgot Password Code
    @GetMapping("/forgot-password")
    public String showForgotPassword() {
        return "forgot_password.html";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, HttpSession session, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {

        User userByEmail = userService.getUserByEmail(email);

        if (ObjectUtils.isEmpty(userByEmail)) {
            session.setAttribute("errorMsg", "Invalid email");
        } else {

            String resetToken = UUID.randomUUID().toString();
            userService.updateUserResetToken(email, resetToken);

            // Generate URL :
            // http://localhost:8080/reset-password?token=sfgdbgfswegfbdgfewgvsrg

            String url = CommonUtil.generateUrl(request) + "/reset-password?token=" + resetToken;

            Boolean sendMail = commonUtil.sendMail(url, email);

            if (sendMail) {
                session.setAttribute("succMsg", "Please check your email..Password Reset link sent");
            } else {
                session.setAttribute("errorMsg", "Somethong wrong on server ! Email not send");
            }
        }

        return "redirect:/forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPassword(@RequestParam String token, HttpSession session, Model m) {
        User userByToken = userService.getUserByToken(token);
        if (userByToken == null) {
            m.addAttribute("msg", "Your link is invalid or expired!!!");
            return "message";
        }
        m.addAttribute("token", token);
        return "reset_password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String password, HttpSession session,
                                Model m) {
        User userByToken = userService.getUserByToken(token);

        if (userByToken == null) {
            m.addAttribute("errorMsg", "Your link is invalid or expired!!!");
            return "message";
        } else {
            userByToken.setPasswordHash(passwordEncoder.encode(password));
            userByToken.setResetToken(null);
            userService.updateUser(userByToken);
            /* session.setAttribute("succMsg", "Password change successfully!"); */
            m.addAttribute("msg", "Password change successfully!");
            return "message";
        }

    }
}
