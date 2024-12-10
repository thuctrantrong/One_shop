package vn.iotstar.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.OrderRequest;
import vn.iotstar.entity.ProductOrder;

import java.util.List;

@Service
public interface OrderService {
    public void saveOrder(Integer userid, OrderRequest orderRequest) throws Exception;

    public ProductOrder updateOrderStatus(int id, String status);

    public List<ProductOrder> getOrderByUser(int userId);

    public Page<ProductOrder> getAllOrdersPagination(Integer pageNo, Integer pageSize);

    public ProductOrder getOrdersByOrderId(String trim);
}
