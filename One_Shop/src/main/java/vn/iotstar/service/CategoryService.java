package vn.iotstar.service;

import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategory();
    public Category updateCategory(int categoryId, Category Category);
    public boolean deleteCategory(int productId);
    List<Category> findByOwnerId(int categoryId);

}
