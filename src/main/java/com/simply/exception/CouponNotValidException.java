package com.simply.exception;

public class CouponNotValidException extends Exception {
    public CouponNotValidException(String message) {
        super(message);
    }
}
