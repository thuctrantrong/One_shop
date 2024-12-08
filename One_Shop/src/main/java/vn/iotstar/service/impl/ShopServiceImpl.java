package vn.iotstar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Shop;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.repository.ShopRepository;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.service.ShopService;
import vn.iotstar.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Shop> findAllShop() {
        return shopRepository.findShopsWithCondition();
    }

    @Override
    public Shop updateShop(int shopId, Shop shop) {
        Optional<Shop> finds = shopRepository.findById(shopId);
        if (finds.isPresent()) {
            Shop existingShop = finds.get();

            existingShop.setName(shop.getName());
            existingShop.setDescription(shop.getDescription());
            existingShop.setCreatedAt(shop.getCreatedAt());

            return shopRepository.save(existingShop);
        }
        return null;
    }

    @Override
    public boolean deleteShop(int shopId) {
        Optional<Shop> shop = shopRepository.findById(shopId);
        if (shop.isPresent()) {
            Shop existingShop = shop.get();
            existingShop.setCondition(0);
            shopRepository.save(existingShop);
            List<Product> products = productRepository.findByOwnerId(shopId);
            for (Product product : products) {
                product.setCondition(0);
                productRepository.save(product);
            }
            return true;
        }
         return false;
    }

    @Override
    public List<Shop> findByOwnerId(int userId) {
        return List.of();
    }
}
