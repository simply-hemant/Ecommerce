package simply.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simply.Ecommerce.model.Wishlist;

@Repository
public interface WishlistRepo extends JpaRepository<Wishlist, Long> {

    Wishlist findByUser_UserId(Long userId);

}
