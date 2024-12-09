package vn.iotstar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Promotion;
import vn.iotstar.entity.Shop;
import vn.iotstar.repository.PromotionRepository;
import vn.iotstar.service.PromotionService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public List<Promotion> findAllPromotion() {
        return promotionRepository.findPromotionsWithCondition();
    }

    @Override
    public Promotion updatePromotion(int promotionId, Promotion promotion) {
        // Tìm đối tượng Promotion trong cơ sở dữ liệu
        Optional<Promotion> finds = promotionRepository.findById(promotionId);

        if (finds.isPresent()) {
            Promotion existingPromotion = finds.get();

            if (promotion.getStartDate().isAfter(promotion.getEndDate())) {
                throw new IllegalArgumentException("Ngày bắt đầu không thể lớn hơn ngày kết thúc.");
            }

            if (promotion.getEndDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Ngày kết thúc không thể trong quá khứ.");
            }

            if (promotion.getStartDate().isBefore(existingPromotion.getCreatedAt())) {
                throw new IllegalArgumentException("Ngày bắt đầu không thể sớm hơn ngày tạo.");
            }

            if (promotion.getDiscountValue() <= 0) {
                throw new IllegalArgumentException("Giá trị giảm giá phải lớn hơn 0.");
            }
            existingPromotion.setName(promotion.getName());
            existingPromotion.setStartDate(promotion.getStartDate());
            existingPromotion.setEndDate(promotion.getEndDate());
            existingPromotion.setDiscountValue(promotion.getDiscountValue());

            return promotionRepository.save(existingPromotion);
        }
        return null;
    }

    @Override
    public Promotion addPromotion(Promotion promotion) {
        if (promotion.getDiscountValue() <= 0) {
            throw new IllegalArgumentException("Giá trị giảm giá phải lớn hơn 0.");
        }

        if (promotion.getStartDate().isAfter(promotion.getEndDate())) {
            throw new IllegalArgumentException("Ngày bắt đầu không thể sau ngày kết thúc.");
        }

        if (promotion.getEndDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Ngày kết thúc không thể trong quá khứ.");
        }

        if (promotion.getCreatedAt() == null) {
            promotion.setCreatedAt(LocalDate.now());
        }
        if ((promotionRepository.countPromotionsWithCondition(promotion.getName())) > 0 ) {
                throw new IllegalArgumentException("Voucher name đã tồn tại trong hệ thống.");
        }
        return promotionRepository.save(promotion);
    }


    @Override
    public boolean deletePromotion(int PromotionId) {
        Optional<Promotion> promotion = promotionRepository.findById(PromotionId);
        if (promotion.isPresent()) {
            Promotion existingPromotion = promotion.get();
            existingPromotion.setCondition(0);
            promotionRepository.save(existingPromotion);
            return true;
        }
        return false;
    }

    @Override
    public List<Promotion> findByOwnerId(int userId) {
        return List.of();
    }
}
