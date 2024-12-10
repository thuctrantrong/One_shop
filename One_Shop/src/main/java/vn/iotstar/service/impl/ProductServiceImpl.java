package vn.iotstar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.entity.Product;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.service.ProductService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRepository categoryRepository;



    @Override
    public List<Product> findAllProduct() {
        return productRepository.findProductsWithCondition();
    }

    @Override
    public Product updateProduct(int productId, Product product, MultipartFile file) {
        Optional<Product> finds = productRepository.findById(productId);
        if (finds.isPresent()) {
            Product existingProduct = finds.get();

            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setStock(product.getStock());

            if (file != null && !file.isEmpty()) {
                String imageUrl = saveImage(file);
                existingProduct.setImageUrl(imageUrl);
            }

            return productRepository.save(existingProduct);
        }
        return null;
    }

    public String saveImage(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

            Path path = Paths.get("uploads/" + fileName);

            Files.createDirectories(path.getParent());

            file.transferTo(path);

            return "/uploads/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }

    @Override
    public boolean deleteProduct(int shopId) {
        Optional<Product> Product = productRepository.findById(shopId);
        if (Product.isPresent()) {
            Product existingProduct = Product.get();
            existingProduct.setCondition(0);
            productRepository.save(existingProduct);
            return true;
        }
        return false;
    }

    @Override
    public List<Product> findByOwnerId(int shopId) {
        return List.of();
    }

    @Override
    public Product addProduct(Product product, MultipartFile file) throws Exception {

        product.setName(product.getName());
        product.setDescription(product.getDescription());
        product.setPrice(product.getPrice());
        product.setStock(product.getStock());
        product.setCondition(product.getCondition());
        product.setCreatedAt(LocalDateTime.now());
        product.setCategory(product.getCategory());
        if (file != null && !file.isEmpty()) {
            String imageUrl = saveImage(file);
            if (imageUrl == null) {
                throw new Exception("Failed to save image");
            }
            product.setImageUrl(imageUrl);
        }
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new Exception("Error saving product: " + e.getMessage(), e);
        }
    }

}
