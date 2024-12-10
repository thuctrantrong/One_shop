package vn.iotstar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.*;
import vn.iotstar.repository.CartRepository;
import vn.iotstar.repository.ProductOrderRepository;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.service.OrderService;
import vn.iotstar.util.CommonUtil;
import vn.iotstar.util.OrderStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductOrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void saveOrder(Integer userid, OrderRequest orderRequest) throws Exception {
        // Lấy danh sách giỏ hàng của người dùng
        List<Cart> carts = cartRepository.findByUser_UserID(userid);

        if (carts == null || carts.isEmpty()) {
            throw new Exception("Cart is empty for user ID: " + userid);
        }

        // Danh sách lưu trữ tất cả đơn hàng (ProductOrder) để gửi email sau
        List<ProductOrder> productOrders = new ArrayList<>();

        for (Cart cart : carts) {
            if (cart.getProduct() == null) {
                System.err.println("Product is null for Cart ID: " + cart.getCartId());
                continue;
            }

            if (cart.getUser() == null) {
                System.err.println("User is null for Cart ID: " + cart.getCartId());
                continue;
            }

            // Tạo đơn hàng
            ProductOrder order = new ProductOrder();
            order.setOrderId(UUID.randomUUID().toString());
            order.setOrderDate(LocalDate.now());
            order.setProduct(cart.getProduct());
            order.setPrice(cart.getProduct().getPrice());
            order.setQuantity(cart.getQuantity());
            order.setUser(cart.getUser());
            order.setStatus(OrderStatus.IN_PROGRESS.getName());
            order.setPaymentType(orderRequest.getPaymentType());

            // Thiết lập thông tin địa chỉ giao hàng
            OrderAddress address = new OrderAddress();
            address.setFirstName(orderRequest.getFirstName());
            address.setLastName(orderRequest.getLastName());
            address.setEmail(orderRequest.getEmail());
            address.setMobileNo(orderRequest.getMobileNo());
            address.setAddress(orderRequest.getAddress());
            address.setCity(orderRequest.getCity());
            order.setOrderAddress(address);

            // Lưu đơn hàng
            ProductOrder savedOrder = orderRepository.save(order);
            productOrders.add(savedOrder);

            // Cập nhật tồn kho sản phẩm
            Product product = cart.getProduct();
            if (product.getStock() >= cart.getQuantity()) {
                product.setStock(product.getStock() - cart.getQuantity());
            } else {
                System.err.println("Not enough stock for product ID: " + product.getProductId());
                product.setCondition(0); // Đánh dấu sản phẩm không còn hoạt động
            }

            productRepository.save(product);
        }

        // Gửi email sau khi lưu tất cả các đơn hàng
        if (!productOrders.isEmpty()) {
            commonUtil.sendMailForProductOrder(productOrders, "success");
        }

        // Xóa tất cả các sản phẩm trong giỏ hàng
        cartRepository.deleteAll(carts);
    }

    @Override
    public ProductOrder updateOrderStatus(int id, String status) {
        Optional<ProductOrder> findById = orderRepository.findById(id);
        if (findById.isPresent()) {
            ProductOrder productOrders = findById.get();
            productOrders.setStatus(status);
            ProductOrder updateOrder = orderRepository.save(productOrders);
            return updateOrder;
        }
        return null;
    }

    @Override
    public List<ProductOrder> getOrderByUser(int userId) {
        List<ProductOrder> orders = orderRepository.findByUser_UserID(userId);
        return orders;
    }

    @Override
    public Page<ProductOrder> getAllOrdersPagination(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return orderRepository.findAll(pageable);
    }

    @Override
    public ProductOrder getOrdersByOrderId(String orderId) {
        return orderRepository.findByOrderId(orderId);
    }

}
