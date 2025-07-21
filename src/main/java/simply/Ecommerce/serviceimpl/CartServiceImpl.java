<<<<<<< HEAD
package simply.Ecommerce.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import simply.Ecommerce.exception.ProductException;
import simply.Ecommerce.model.Cart;
import simply.Ecommerce.model.CartItem;
import simply.Ecommerce.model.Product;
import simply.Ecommerce.model.User;
import simply.Ecommerce.repository.CartItemRepo;
import simply.Ecommerce.repository.CartRepo;
import simply.Ecommerce.repository.ProductRepo;
import simply.Ecommerce.service.CartService;
import simply.Ecommerce.service.ProductService;

import java.util.ArrayList;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;

    @Override
    public CartItem addCartItem(User user, Product product, String size, int quantity) throws ProductException {

        Cart cart = findUserCart(user);

        CartItem isPresent = cartItemRepo.findByCartAndProductAndSize(cart, product, size);

        if(isPresent == null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);

            cartItem.setQuantity(quantity);
            cartItem.setUserId(user.getUserId());

            int totalPrice = quantity * product.getSellingPrice();
            cartItem.setSellingPrice(totalPrice);
            cartItem.setMrpPrice(quantity * product.getMrpPrice());
            cartItem.setSize(size);

            cart.getCartItems().add(cartItem);
            cartItem.setCart(cart);

            return  cartItemRepo.save(cartItem);
        }

        return isPresent;
    }

    @Override
    public Cart findUserCart(User user) {

        Cart cart = cartRepo.findByUser_UserId(user.getUserId());

        if (cart == null) {
            // 🔧 Create a new cart for the user
            cart = new Cart();
            cart.setUser(user);
            cart.setCartItems(new HashSet<>());
            cart.setTotalItem(0);
            cart.setTotalSellingPrice(0);
            cart.setTotalMrpPrice(0);
            cart.setDiscount(0);
            cart.setCouponPrice(0);
            return cartRepo.save(cart);
        }

        // 🔁 Calculate prices if cart exists
        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        for (CartItem cartItem : cart.getCartItems()) {
            totalPrice += cartItem.getMrpPrice();
            totalDiscountedPrice += cartItem.getSellingPrice();
            totalItem += cartItem.getQuantity();
        }

        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalItem(totalItem);
        cart.setTotalSellingPrice(totalDiscountedPrice - cart.getCouponPrice());
        cart.setDiscount(calculateDiscountPercentage(totalPrice, totalDiscountedPrice));

        return cartRepo.save(cart);
    }

    private static int calculateDiscountPercentage(double mrpPrice, double sellingPrice) {

        if(mrpPrice <= 0)  return 0;

        double discount = mrpPrice - sellingPrice;
        double discountPercentage = (discount / mrpPrice) * 100;
        return (int) discountPercentage;
    }
}
=======
package simply.Ecommerce.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import simply.Ecommerce.exception.ProductException;
import simply.Ecommerce.model.Cart;
import simply.Ecommerce.model.CartItem;
import simply.Ecommerce.model.Product;
import simply.Ecommerce.model.User;
import simply.Ecommerce.repository.CartItemRepo;
import simply.Ecommerce.repository.CartRepo;
import simply.Ecommerce.repository.ProductRepo;
import simply.Ecommerce.service.CartService;
import simply.Ecommerce.service.ProductService;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final ProductService productService;
    private final CartItemRepo cartItemRepo;

    @Override
    public CartItem addCartItem(User user, Product product, String size, int quantity) throws ProductException {

        Cart cart=findUserCart(user);

        CartItem isPresent = cartItemRepo.findByCartAndProductAndSize(cart, product, size);

        if(isPresent == null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);

            cartItem.setQuantity(quantity);
            cartItem.setUserId(user.getUserId());

            int totalPrice = quantity * product.getSellingPrice();
            cartItem.setSellingPrice(totalPrice);
            cartItem.setMrpPrice(quantity * product.getMrpPrice());
            cartItem.setSize(size);

            cart.getCartItems().add(cartItem);
            cartItem.setCart(cart);

            return  cartItemRepo.save(cartItem);
        }

        return isPresent;
    }

    @Override
    public Cart findUserCart(User user) {

        Cart cart = cartRepo.findByUser_UserId(user.getUserId());

        int totalPrice = 0;
        int totalDiscountedPrince = 0;
        int totalItem = 0;

        for(CartItem cartItem: cart.getCartItems())
        {
            totalPrice = totalPrice + cartItem.getMrpPrice();
            totalDiscountedPrince = totalDiscountedPrince + cartItem.getSellingPrice();
            totalItem = totalItem + cartItem.getQuantity();
        }

        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalItem(cart.getCartItems().size());
        cart.setTotalSellingPrice(totalDiscountedPrince - cart.getCouponPrice());
        cart.setDiscount(calculateDiscountPercentage(totalPrice, totalDiscountedPrince));
        cart.setTotalItem(totalItem);

        return cartRepo.save(cart);
    }

    private static int calculateDiscountPercentage(double mrpPrice, double sellingPrice) {

        if(mrpPrice <= 0)  return 0;

        double discount = mrpPrice - sellingPrice;
        double discountPercentage = (discount / mrpPrice) * 100;
        return (int) discountPercentage;
    }
}
>>>>>>> 852346b (Added DataInitializationComponent and other updates)
