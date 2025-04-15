package simply.Ecommerce.service;

import simply.Ecommerce.exception.WishlistNotFoundException;
import simply.Ecommerce.model.Product;
import simply.Ecommerce.model.User;
import simply.Ecommerce.model.Wishlist;

public interface WishListService {

    Wishlist createWishList(User user);

    Wishlist getWishlistByUserId(User user);

    Wishlist addProductToWishlist(User user, Product product) throws WishlistNotFoundException;

}
