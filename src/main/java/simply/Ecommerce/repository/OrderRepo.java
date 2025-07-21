package simply.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simply.Ecommerce.model.Order;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepo  extends JpaRepository<Order, Long> {

    List<Order> findByUser_UserId(Long userId);

    List<Order> findBySellerId(Long sellerId);

    List<Order> findBySellerIdAndOrderDateBetween(Long sellerId, LocalDateTime startDate, LocalDateTime endDate);


}
