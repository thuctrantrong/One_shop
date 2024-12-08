package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.entity.Product;
import vn.iotstar.service.ProductService;

import java.util.List;

@RequestMapping("/admin/api/product")
@RestController
public class ResultAPIProduct {

    @Autowired
    private ProductService productService;

    // Lấy tất cả sản phẩm
    @GetMapping
    public List<Product> getAllProduct() {
        System.out.println(productService.findAllProduct());
        return productService.findAllProduct();
    }
}
