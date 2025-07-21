package simply.Ecommerce.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import simply.Ecommerce.exception.WishlistNotFoundException;
import simply.Ecommerce.model.Product;
import simply.Ecommerce.model.User;
import simply.Ecommerce.model.Wishlist;
import simply.Ecommerce.repository.WishlistRepo;
import simply.Ecommerce.service.ProductService;
import simply.Ecommerce.service.UserService;
import simply.Ecommerce.service.WishListService;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final WishlistRepo wishlistRepo;

    @Override
    public Wishlist createWishList(User user) {

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);

        return wishlistRepo.save(wishlist);
    }

    @Override
    public Wishlist getWishlistByUserId(User user) {

        Wishlist wishlist = wishlistRepo.findByUser_UserId(user.getUserId());
        if(wishlist == null){
            wishlist = this.createWishList(user);
        }

        return wishlist;
    }

    @Override
    public Wishlist addProductToWishlist(User user, Product product) throws WishlistNotFoundException {

        Wishlist wishlist = this.getWishlistByUserId(user);
        if(wishlist.getProducts().contains(product)){
            wishlist.getProducts().remove(product);
        }
        else wishlist.getProducts().add(product);

        return wishlistRepo.save(wishlist);
    }
}
