package com.simply.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simply.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
