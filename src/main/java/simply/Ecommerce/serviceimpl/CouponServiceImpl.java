package simply.Ecommerce.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import simply.Ecommerce.exception.CouponNotValidException;
import simply.Ecommerce.model.Cart;
import simply.Ecommerce.model.Coupon;
import simply.Ecommerce.model.User;
import simply.Ecommerce.repository.CartRepo;
import simply.Ecommerce.repository.CouponRepo;
import simply.Ecommerce.repository.UserRepo;
import simply.Ecommerce.service.CouponService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepo couponRepo;
    private final CartRepo cartRepo;
    private final UserRepo userRepo;

    @Override
    public Cart applyCoupon(String code, double orderValue, User user) throws Exception {

        Coupon coupon = couponRepo.findByCode(code);
        Cart cart = cartRepo.findByUser_UserId(user.getUserId());

        if(coupon == null){
            throw new CouponNotValidException("Coupon not found");
        }

        if(user.getUsedCoupons().contains(coupon)){
            throw new CouponNotValidException("coupon already used");
        }

        if(orderValue <= coupon.getMinimumOrderValue()){
            throw new CouponNotValidException("valid for minimum order value " +coupon.getMinimumOrderValue());

        }

            if(coupon.isActive() &&
                    !LocalDate.now().isBefore(coupon.getValidityStartDate()) &&
                    !LocalDate.now().isAfter(coupon.getValidityEndDate()))
            {
                user.getUsedCoupons().add(coupon);
                userRepo.save(user);

                double discountedPrice = Math.round((cart.getTotalSellingPrice()* coupon.getDiscountPercentage()) / 100);

                cart.setTotalSellingPrice(cart.getTotalSellingPrice() - discountedPrice);
                cart.setCouponCode(code);
                cart.setCouponPrice((int)discountedPrice);
                return cartRepo.save(cart);
            }

         throw new CouponNotValidException("coupon not valid...");
    }

    @Override
    public Cart removeCoupon(String code, User user) throws Exception {

        Coupon coupon = couponRepo.findByCode(code);

        if(coupon == null){
            throw new CouponNotValidException("Coupon not found");
        }

        user.getUsedCoupons().remove(coupon);

        Cart cart = cartRepo.findByUser_UserId(user.getUserId());
        cart.setTotalSellingPrice(cart.getTotalSellingPrice() + cart.getCouponPrice());
        cart.setCouponCode(null);
        cart.setCouponPrice(0);

        return cartRepo.save(cart);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Coupon createCoupon(Coupon coupon) {

        return couponRepo.save(coupon);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Coupon deleteCoupon(Long couponId) {
            couponRepo.deleteById(couponId);
        return null;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<Coupon> getAllCoupons() {
        return couponRepo.findAll();
    }

    @Override
    public Coupon findCouponById(Long couponId) {
//        return couponRepo.findById(couponId)
//                .orElseThrow(()-> new RuntimeException("coupon not found"));
        Optional<Coupon> optionalCoupon = couponRepo.findById(couponId);

        if (optionalCoupon.isPresent()) {
             return optionalCoupon.get();
        }
        else {
             throw new RuntimeException("Coupon not found");
        }
        }


}
