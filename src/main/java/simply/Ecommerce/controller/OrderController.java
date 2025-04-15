package simply.Ecommerce.controller;

import com.razorpay.PaymentLink;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simply.Ecommerce.Enum.PaymentMethod;
import simply.Ecommerce.Response.PaymentLinkResponse;
import simply.Ecommerce.exception.OrderException;
import simply.Ecommerce.model.*;
import simply.Ecommerce.repository.OrderItemRepo;
import simply.Ecommerce.repository.PaymentOrderRepo;
import simply.Ecommerce.service.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final UserService userService;
    private final CartService cartService;
    private final OrderService orderService;
    private final SellerReportService sellerReportService;
    private final SellerService sellerService;
    private final PaymentService paymentService;
    private final PaymentOrderRepo paymentOrderRepo;

    @PostMapping
    public ResponseEntity<PaymentLinkResponse> createOrderHandler(
            @RequestBody Address shippingAddress,
            @RequestParam PaymentMethod paymentMethod,
            @RequestHeader("Authorization") String jwt)
            throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findUserCart(user);

        Set<Order> orders =orderService.createOrder(user, shippingAddress,cart);

        PaymentOrder paymentOrder=paymentService.createOrder(user,orders);

        PaymentLinkResponse res = new PaymentLinkResponse();

        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
            PaymentLink payment=paymentService.createRazorpayPaymentLink(user,
                    paymentOrder.getAmount(),
                    paymentOrder.getPaymentOrderId());
            String paymentUrl=payment.get("short_url");
            String paymentUrlId=payment.get("id");


            res.setPayment_link_url(paymentUrl);
            paymentOrder.setPaymentLinkId(paymentUrlId);
            paymentOrderRepo.save(paymentOrder);
        }
        else{
            String paymentUrl=paymentService.createStripePaymentLink(user,
                    paymentOrder.getAmount(),
                    paymentOrder.getPaymentOrderId());
            res.setPayment_link_url(paymentUrl);
        }

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistory(
            @RequestHeader("Authorization")
            String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.userOrderHistory(user.getUserId());

        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId, @RequestHeader("Authorization")
        String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.findOrderById(orderId);

        return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
    }

    @GetMapping("/item/{orderItemId}")
    public ResponseEntity<OrderItem> getOrderByItemId(
            @PathVariable Long orderItemId, @RequestHeader("Authorization")
            String jwt) throws Exception{

        User user = userService.findUserByJwtToken(jwt);
        OrderItem orderItem = orderService.getOrderItemById(orderItemId);       //orderItem

        return new ResponseEntity<>(orderItem, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(
            @PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception, OrderException, SecurityException{

        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.findOrderById(orderId);

        Seller seller = sellerService.getSellerById(order.getSellerId());
        SellerReport report = sellerReportService.getSellerReport(seller);
        sellerReportService.updateSellerReport(report);

        return ResponseEntity.ok(order);

    }

}
