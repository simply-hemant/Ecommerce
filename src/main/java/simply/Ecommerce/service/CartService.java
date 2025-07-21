package simply.Ecommerce.service;

import org.springframework.stereotype.Service;
import simply.Ecommerce.exception.ProductException;
import simply.Ecommerce.model.Cart;
import simply.Ecommerce.model.CartItem;
import simply.Ecommerce.model.Product;
import simply.Ecommerce.model.User;

@Service
public interface CartService {

    CartItem addCartItem(User user,
                         Product product,
                         String size,
                         int quantity) throws ProductException;

    Cart findUserCart(User user);

}
