package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Promotion;
import vn.iotstar.entity.Shop;

import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    @Query("SELECT p FROM Promotion p WHERE p.condition = 1")
    List<Promotion> findPromotionsWithCondition();
    @Query("SELECT COUNT(p) " +
            "FROM Promotion p " +
            "WHERE p.name = :name " +
            "AND p.condition = 1")
    long countPromotionsWithCondition(@Param("name") String name);

}
