package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.service.CategoryService;
import vn.iotstar.service.ProductService;

import java.util.List;
import java.util.Optional;

@RequestMapping("/admin/api/product")
@RestController
@CrossOrigin()
public class ResultAPIProduct {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    // Lấy tất cả sản phẩm
    @GetMapping
    public List<Product> getAllProduct() {
        System.out.println(categoryService.findAllCategory());
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

    @PostMapping("/add")
    public ModelAndView addProduct(
            @RequestParam("addproductName") String name,
            @RequestParam("addproductDescription") String description,
            @RequestParam("addproductPrice") double price,
            @RequestParam("addproductStock") int stock,
            @RequestParam(value = "addproductCategory", required = false) Integer categoryId, // Đảm bảo có category
            @RequestParam(value = "addproductImageUrl", required = false) MultipartFile image, // Image có thể là null
            RedirectAttributes redirectAttributes) {

        if (price <= 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "Price must be greater than zero!");
            return new ModelAndView("redirect:/admin/product/add");
        }

        if (stock < 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "Stock cannot be negative!");
            return new ModelAndView("redirect:/admin/product/add");
        }



        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCondition(1);
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        Category category = categoryOptional.get();
        product.setCategory(category);
        try {
            Product addProduct = productService.addProduct(product, image);
            redirectAttributes.addFlashAttribute("message", "Product added successfully!");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception e) {

            throw new RuntimeException(e);
        }

        return new ModelAndView("redirect:/admin/product");
    }

}
