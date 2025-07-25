package com.simply.service;


import com.simply.exception.WishlistNotFoundException;
import com.simply.model.Product;
import com.simply.model.User;
import com.simply.model.Wishlist;

public interface WishlistService {

    Wishlist createWishlist(User user);

    Wishlist getWishlistByUserId(User user);

    Wishlist addProductToWishlist(User user, Product product) throws WishlistNotFoundException;

}

