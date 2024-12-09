package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Shop;
import vn.iotstar.service.CategoryService;
import vn.iotstar.service.ShopService;

import java.util.List;

@RequestMapping("/admin/api/category")
@RestController
public class ResultAPICategory {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategory() {
        return categoryService.findAllCategory();
    }
    @PutMapping("/edit/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable("categoryId") int categoryId, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(categoryId, category);

        if (updatedCategory != null) {
            return ResponseEntity.ok(updatedCategory);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
