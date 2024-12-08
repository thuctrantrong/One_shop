package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p, p.category.name FROM Product p WHERE p.condition = 1")
    List<Product> findProductsWithCondition();
    @Query("SELECT p FROM Product p WHERE p.shop.shopId = :shopId AND p.condition = 1")
    List<Product> findByOwnerId(@Param("shopId") int shopId);
}
