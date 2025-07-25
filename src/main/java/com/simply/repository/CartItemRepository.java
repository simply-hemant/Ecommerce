package com.simply.repository;

import com.simply.model.Cart;
import com.simply.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import com.simply.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);


}
