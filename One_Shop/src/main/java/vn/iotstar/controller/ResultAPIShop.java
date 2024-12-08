package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.entity.Shop;
import vn.iotstar.service.ShopService;

import java.util.List;

@RequestMapping("/admin/api/shop")
@RestController
public class ResultAPIShop {
    @Autowired
    private ShopService shopService;

    @GetMapping
    public List<Shop> getAllShop() {
        System.out.println(shopService.findAllShop());
        return shopService.findAllShop();
    }
    @PutMapping("/edit/{shopId}")
    public ResponseEntity<Shop> updateShop(@PathVariable("shopId") int shopId, @RequestBody Shop shop) {
        Shop updatedShop = shopService.updateShop(shopId, shop);

        if (updatedShop != null) {
            return ResponseEntity.ok(updatedShop);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/delete/{shopId}")
    public ResponseEntity<Void> deleteShop(@PathVariable("shopId") int shopId) {
        boolean isDeleted = shopService.deleteShop(shopId);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
