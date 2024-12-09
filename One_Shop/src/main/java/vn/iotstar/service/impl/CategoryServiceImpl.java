package vn.iotstar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.repository.ShopRepository;
import vn.iotstar.service.CategoryService;
import vn.iotstar.service.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Category> findAllCategory() {
        return categoryRepository.findCategoryWithCondition();
    }

    @Override
    public Category updateCategory(int categoryId, Category category) {
        Optional<Category> finds = categoryRepository.findById(categoryId);
        if (finds.isPresent()) {
            Category existingCategory = finds.get();
            existingCategory.setName(category.getName());
            existingCategory.setDescription(category.getDescription());
            existingCategory.setCondition(category.getCondition());
            return categoryRepository.save(existingCategory);
        }
        return null;
    }

    @Override
    public boolean deleteCategory(int categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            Category existingCategory = category.get();
            existingCategory.setCondition(0);
            categoryRepository.save(existingCategory);
            return true;
        }
        return false;
    }

    @Override
    public List<Category> findByOwnerId(int productId) {
        return List.of();
    }
}
