package simply.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simply.Ecommerce.model.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

  // Cart findByUserId(Long userId);
      Cart findByUser_UserId(Long userId);

}
