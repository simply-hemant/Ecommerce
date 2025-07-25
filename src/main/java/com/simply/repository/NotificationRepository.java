package com.simply.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simply.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {



}
