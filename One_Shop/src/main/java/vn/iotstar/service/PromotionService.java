package vn.iotstar.service;

import vn.iotstar.entity.Promotion;

import java.util.List;

public interface PromotionService {
    List<Promotion> findAllPromotion();
    public Promotion updatePromotion(int PromotionId, Promotion promotion);
    public Promotion addPromotion( Promotion promotion);
    public boolean deletePromotion(int PromotionId);
    List<Promotion> findByOwnerId(int userId);
}
