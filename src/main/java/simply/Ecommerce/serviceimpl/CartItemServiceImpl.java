package simply.Ecommerce.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import simply.Ecommerce.exception.CartItemException;
import simply.Ecommerce.exception.UserException;
import simply.Ecommerce.model.CartItem;
import simply.Ecommerce.model.User;
import simply.Ecommerce.repository.CartItemRepo;
import simply.Ecommerce.service.CartItemService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepo cartItemRepo;

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException {

        CartItem item = findCartItemById(id);
        User cartItemUser = item.getCart().getUser();

        if(cartItemUser.getUserId().equals(userId)){

            item.setQuantity(cartItem.getQuantity());
            item.setMrpPrice(item.getQuantity() * item.getProduct().getMrpPrice());
            item.setSellingPrice(item.getQuantity() * item.getProduct().getSellingPrice());

            return cartItemRepo.save(item);
        }
        else {
            throw new CartItemException("You can't update another users cart_item");
        }
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {

        CartItem cartItem = findCartItemById(cartItemId);

        User cartItemUser = cartItem.getCart().getUser();

        if(cartItemUser.getUserId().equals(userId)){
            cartItemRepo.deleteById(cartItem.getCartitemId());
        }
        else {
            throw new UserException("you can't remove another users item");
        }

    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {

        Optional<CartItem> opt = cartItemRepo.findById(cartItemId);

        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new CartItemException("cartItem not found with id : " + cartItemId);
        }

    }
}
