package simply.Ecommerce.service;

import simply.Ecommerce.exception.UserException;
import simply.Ecommerce.model.Cart;
import simply.Ecommerce.model.Coupon;
import simply.Ecommerce.model.User;

import java.util.List;

public interface CouponService {

    Cart applyCoupon(String code, double orderValue, User user) throws Exception;
    Cart removeCoupon(String code, User user) throws Exception;
    Coupon createCoupon(Coupon coupon);
    Coupon deleteCoupon(Long couponId);
    List<Coupon> getAllCoupons();
    public Coupon findCouponById(Long couponId);
}
