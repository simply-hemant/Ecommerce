package simply.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simply.Ecommerce.model.Cart;
import simply.Ecommerce.model.CartItem;
import simply.Ecommerce.model.Product;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem ,Long> {

   // CartItem findByCartAndProductSize(Cart cart, Product product, String size);

    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);
}
