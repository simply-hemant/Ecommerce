package simply.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simply.Ecommerce.model.Coupon;

@Repository
public interface CouponRepo extends JpaRepository<Coupon, Long> {

    Coupon findByCode(String code);

}
