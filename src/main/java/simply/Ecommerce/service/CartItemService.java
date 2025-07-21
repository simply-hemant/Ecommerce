package simply.Ecommerce.service;

import simply.Ecommerce.exception.CartItemException;
import simply.Ecommerce.exception.UserException;
import simply.Ecommerce.model.CartItem;
import simply.Ecommerce.model.Category;

import java.util.function.LongFunction;

public interface CartItemService {

    CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException;   //userid, id of cart item, cart item

    void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;

    CartItem findCartItemById(Long cartItemId) throws CartItemException;

}
