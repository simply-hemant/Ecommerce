package simply.Ecommerce.controller;

import com.stripe.model.tax.Registration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simply.Ecommerce.Response.ApiResponse;
import simply.Ecommerce.model.Cart;
import simply.Ecommerce.model.Coupon;
import simply.Ecommerce.model.User;
import simply.Ecommerce.repository.CouponRepo;
import simply.Ecommerce.service.CouponService;
import simply.Ecommerce.service.UserService;

import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class AdminCouponController {

    private final UserService userService;
    private final CouponService couponService;
    private final CouponRepo couponRepo;

    @PostMapping("/apply")
    public ResponseEntity<Cart> applyCoupon (
            @RequestParam String apply,
            @RequestParam String code,
            @RequestParam double orderValue,
            @RequestHeader("Authorization") String jwt
    ) throws Exception
    {
        User user = userService.findUserByJwtToken(jwt);

        Cart cart;

        if(apply.equals("true")){
            cart = couponService.applyCoupon(code, orderValue, user);
        }
        else{
            cart = couponService.removeCoupon(code,user);
        }

        return ResponseEntity.ok(cart);
    }

    // Admin operations

    @PostMapping("/admin/create")
    public ResponseEntity<Coupon> createCoupon (@RequestBody Coupon coupon){

        Coupon createCoupon = couponService.createCoupon(coupon);

        return ResponseEntity.ok(createCoupon);
    }

    @DeleteMapping("/admin/delete/{couponId}")
    public ResponseEntity<ApiResponse> deleteCoupon (@PathVariable Long couponId ){

        Coupon deleteCoupon = couponService.deleteCoupon(couponId);
        couponRepo.deleteById(couponId);

        ApiResponse res = new ApiResponse("Coupon removed",true);

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<Coupon>> getAllCoupons()
    {
        List<Coupon> coupons = couponService.getAllCoupons();
        return ResponseEntity.ok(coupons);
    }

}
