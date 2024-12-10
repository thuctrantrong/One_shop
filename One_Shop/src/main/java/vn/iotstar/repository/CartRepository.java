package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.iotstar.entity.Cart;
import vn.iotstar.entity.Category;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    public List<Cart> findByUser_UserID(int userID);

    List<Cart> findByUser_UserIDAndProduct_ProductId(int userID, int productId);


}
