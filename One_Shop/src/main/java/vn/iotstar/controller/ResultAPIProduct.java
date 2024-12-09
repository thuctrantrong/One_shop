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

    @PutMapping("/edit/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable("productId") int productId,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("stock") int stock,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        System.out.println("Product ID: " + productId);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Price: " + price);
        System.out.println("Stock: " + stock);

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);

        Product updatedProduct = productService.updateProduct(productId, product, file);

        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }




    // Xóa sản phẩm
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") int productId) {
        boolean isDeleted = productService.deleteProduct(productId);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
