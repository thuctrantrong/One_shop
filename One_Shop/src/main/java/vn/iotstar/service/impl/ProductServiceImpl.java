package vn.iotstar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;
import vn.iotstar.entity.User;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.repository.ShopRepository;
import vn.iotstar.service.ProductService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

            // Lưu sản phẩm đã cập nhật vào cơ sở dữ liệu
            return productRepository.save(existingProduct);
        }
        return null;
    }

    public String saveImage(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

            Path path = Paths.get("uploads/product-images/" + fileName);

            Files.createDirectories(path.getParent());

            file.transferTo(path);

            return "/uploads/product-images/" + fileName;
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
}
