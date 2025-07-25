package com.simply.response;

import com.simply.dto.OrderHistory;
import com.simply.model.Cart;
import com.simply.model.Product;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionResponse {
    private String functionName;
    private Cart userCart;
    private OrderHistory orderHistory;
    private Product product;
}
