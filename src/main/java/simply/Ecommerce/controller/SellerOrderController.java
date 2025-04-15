package simply.Ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simply.Ecommerce.Enum.OrderStatus;
import simply.Ecommerce.Response.ApiResponse;
import simply.Ecommerce.exception.OrderException;
import simply.Ecommerce.model.Order;
import simply.Ecommerce.model.Seller;
import simply.Ecommerce.service.OrderService;
import simply.Ecommerce.service.SellerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller/orders")
public class SellerOrderController {

    private final OrderService orderService;
    private final SellerService sellerService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrderHandler(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        Seller seller = sellerService.getSellerProfile(jwt);
        List<Order> orders = orderService.getSellersOrder(seller.getId());

        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/status/{orderStatus}")
    public ResponseEntity<Order> updateOrderHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId,
            @PathVariable OrderStatus orderStatus
    ) throws OrderException {

        Order order = orderService.updateOrderStatus(orderId, orderStatus);

        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);

    }


    public ResponseEntity<ApiResponse> deleteOrderHandler(
            @PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt) throws OrderException {

        orderService.deleteOrder(orderId);
        ApiResponse res = new ApiResponse("Order Deleted Successfully", true);

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

    }

}
