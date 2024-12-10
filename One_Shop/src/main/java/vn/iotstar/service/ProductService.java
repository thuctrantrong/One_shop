package vn.iotstar.service;

import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProduct();
    public Product updateProduct(int productId, Product product, MultipartFile file);
    public boolean deleteProduct(int shopId);
    List<Product> findByOwnerId(int shopId);

    public Product addProduct(Product product, MultipartFile file) throws Exception;
}
