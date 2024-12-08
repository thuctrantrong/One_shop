package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
