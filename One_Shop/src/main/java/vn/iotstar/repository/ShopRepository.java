package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.iotstar.entity.Shop;
import vn.iotstar.entity.User;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
    @Query("SELECT s FROM Shop s WHERE s.condition = 1")
    List<Shop> findShopsWithCondition();

    @Query("SELECT s FROM Shop s WHERE s.owner.userId = :userId AND s.condition = 1")
    List<Shop> findByOwnerId(@Param("userId") int userId);
}
