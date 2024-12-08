package vn.iotstar.service;

import vn.iotstar.entity.Shop;

import java.util.List;

public interface ShopService {
    List<Shop> findAllShop();
    public Shop updateShop(int shopId, Shop shop);
    public boolean deleteShop(int shopId);
    List<Shop> findByOwnerId(int userId);
}
