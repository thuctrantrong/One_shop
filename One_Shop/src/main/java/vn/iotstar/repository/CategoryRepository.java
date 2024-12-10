package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category c WHERE c.condition = 1")
    List<Category> findCategoryWithCondition();

}
