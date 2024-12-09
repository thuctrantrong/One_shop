package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Promotion;
import vn.iotstar.service.PromotionService;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/admin/api/promotion")
@RestController
public class ResultAPIPromotion {

    @Autowired
    private PromotionService promotionService;
    @GetMapping
    public List<Promotion> getAllPromotion() {
        System.out.println(promotionService.findAllPromotion());
        return promotionService.findAllPromotion();
    }
    @PutMapping("/edit/{promotionId}")
    public ResponseEntity<Promotion> updatePromotion(@PathVariable("promotionId") int promotionId, @RequestBody Promotion promotion) {
        Promotion updatedPromotion = promotionService.updatePromotion(promotionId, promotion);

        if (updatedPromotion != null) {
            return ResponseEntity.ok(updatedPromotion);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/delete/{promotionId}")
    public ResponseEntity<Void> deletePromotion(@PathVariable("promotionId") int promotionId) {
        boolean isDeleted = promotionService.deletePromotion(promotionId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping("/add")
    public ModelAndView addPromotion(
            @RequestParam("voucherName") String name,
            @RequestParam("voucherDiscount") double discountValue,
            @RequestParam("voucherStartDate") String startDate,
            @RequestParam("voucherEndDate") String endDate,
            RedirectAttributes redirectAttributes) {

        Promotion promotion = new Promotion();
        promotion.setName(name);
        promotion.setDiscountValue(discountValue);
        promotion.setStartDate(LocalDate.parse(startDate));
        promotion.setEndDate(LocalDate.parse(endDate));
        promotion.setCondition(1);


        try {
            Promotion createdPromotion = promotionService.addPromotion(promotion);
            redirectAttributes.addFlashAttribute("message", "Voucher added successfully!");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        return new ModelAndView("redirect:/admin/promotion");
    }


}
