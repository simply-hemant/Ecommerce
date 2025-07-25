package com.simply.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simply.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	 Cart findByUserId(Long userId);
}
