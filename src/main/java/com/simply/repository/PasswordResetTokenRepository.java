package com.simply.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simply.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
	PasswordResetToken findByToken(String token);
}
