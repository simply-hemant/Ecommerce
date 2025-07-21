package simply.Ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simply.Ecommerce.model.Product;
import simply.Ecommerce.model.User;
import simply.Ecommerce.model.Wishlist;
import simply.Ecommerce.service.ProductService;
import simply.Ecommerce.service.UserService;
import simply.Ecommerce.service.WishListService;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishListService wishListService;
    private final UserService userService;
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Wishlist> createWishList(@RequestBody User user){

        Wishlist wishlist = wishListService.createWishList(user);
        return ResponseEntity.ok(wishlist);
    }

    @GetMapping
    public ResponseEntity<Wishlist> getWishlistByUserId(
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Wishlist wishlist = wishListService.getWishlistByUserId(user);

        return ResponseEntity.ok(wishlist);

    }

    @PostMapping("/add-product/{productId}")
    public ResponseEntity<Wishlist> addProductToWishlist(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long productId ) throws Exception {

        Product product = productService.findProductById(productId);
        User user = userService.findUserByJwtToken(jwt);

        Wishlist updateWishlist = wishListService.addProductToWishlist(user, product);

        return ResponseEntity.ok(updateWishlist);
    }

}
