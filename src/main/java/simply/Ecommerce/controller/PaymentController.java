package simply.Ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simply.Ecommerce.Response.ApiResponse;
import simply.Ecommerce.Response.PaymentLinkResponse;
import simply.Ecommerce.model.*;
import simply.Ecommerce.repository.CartItemRepo;
import simply.Ecommerce.repository.CartRepo;
import simply.Ecommerce.service.PaymentService;
import simply.Ecommerce.service.SellerReportService;
import simply.Ecommerce.service.SellerService;
import simply.Ecommerce.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final UserService userService;
    private final PaymentService paymentService;
    private final SellerService sellerService;
    private final SellerReportService sellerReportService;
    private final CartRepo cartRepo;

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse> paymentSuccessHandler(
            @PathVariable String paymentId,
            @RequestParam String paymentLinkId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        PaymentLinkResponse paymentLinkResponse;

        PaymentOrder paymentOrder = paymentService
                .getPaymentOrderByPaymentId(paymentLinkId);

        boolean paymentSuccess = paymentService.ProceedPaymentOrder(
                paymentOrder,
                paymentId,
                paymentLinkId
        );

        if(paymentSuccess){
            for(Order order:paymentOrder.getOrders()){
//              transactionService.createTransaction(order);
                Seller seller = sellerService.getSellerById(order.getSellerId());
                SellerReport report = sellerReportService.getSellerReport(seller);
                report.setTotalOrders(report.getTotalOrders()+1);
                report.setTotalEarnings(report.getTotalEarnings()+order.getTotalSellingPrice());
                report.setTotalSales(report.getTotalSales()+order.getOrderItems().size());
                sellerReportService.updateSellerReport(report);
            }

            Cart cart = cartRepo.findByUser_UserId(user.getUserId());
            cart.setCouponPrice(0);
            cart.setCouponCode(null);

            cartRepo.save(cart);

        }

        ApiResponse res = new ApiResponse();
        res.setMessage("Payment successful");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }

}
