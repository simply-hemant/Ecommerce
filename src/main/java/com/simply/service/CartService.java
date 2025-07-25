package com.simply.service;

import com.simply.exception.ProductException;
import com.simply.model.Cart;
import com.simply.model.CartItem;
import com.simply.model.Product;
import com.simply.model.User;

public interface CartService {
	
	public CartItem addCartItem(User user,
								Product product,
								String size,
								int quantity) throws ProductException;
	
	public Cart findUserCart(User user);

}
