package simply.Ecommerce.serviceimpl;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import netscape.javascript.JSObject;
import org.aspectj.weaver.ast.Or;
import org.json.JSONObject;
import org.json.JSONPointer;
import org.springframework.stereotype.Service;
import simply.Ecommerce.Enum.PaymentOrderStatus;
import simply.Ecommerce.Enum.PaymentStatus;
import simply.Ecommerce.model.Order;
import simply.Ecommerce.model.PaymentOrder;
import simply.Ecommerce.model.User;
import simply.Ecommerce.repository.CartRepo;
import simply.Ecommerce.repository.OrderRepo;
import simply.Ecommerce.repository.PaymentOrderRepo;
import simply.Ecommerce.service.PaymentService;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private String apikey = "apikey";
    private String apiSecret = "apisecret";
    private String stripeSecretKey;


    private final OrderRepo orderRepo;
    private final CartRepo cartRepo;
    private final PaymentOrderRepo paymentOrderRepo;

    @Override
    public PaymentOrder createOrder(User user, Set<Order> orders) {

//        Long amount = orders.stream().mapToLong(Order::getTotalSellingPrice).sum();
//        int coupanPrice = cartRepo.findByUser_UserId(user.getUserId()).getCouponPrice();


        Long amount = 0L;

        // Calculate total selling price manually
        for (Order order : orders) {
            amount += order.getTotalSellingPrice();
        }

        int couponPrice = cartRepo.findByUser_UserId(user.getUserId()).getCouponPrice();

        PaymentOrder order = new PaymentOrder();
        order.setUser(user);
        order.setAmount(amount-couponPrice);
        order.setOrders(orders);

        return paymentOrderRepo.save(order);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long Id) throws Exception {

        Optional<PaymentOrder> optionalPaymentOrder = paymentOrderRepo.findById(Id);
        if(optionalPaymentOrder.isEmpty()){
            throw new Exception("payment order not found with id "+Id);
        }

        return optionalPaymentOrder.get();
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) throws Exception {

        PaymentOrder paymentOrder = paymentOrderRepo.findByPaymentLinkId(paymentId);

        if(paymentOrder == null){
            throw new Exception("payment not found with id "+paymentId);
        }

        return paymentOrder;
    }

    @Override
    public Boolean ProceedPaymentOrder(PaymentOrder paymentOrder,
                                       String paymentId,
                                       String paymentLinkId) throws RazorpayException {

        if (paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING))
        {

            RazorpayClient razorpay = new RazorpayClient(apikey, apiSecret);

            Payment payment = razorpay.payments.fetch(paymentId);

            String status = payment.get("status");
            if(status.equals("captured"))
            {

                Set<Order> orders = paymentOrder.getOrders();
                for(Order order:orders)
                {
                    order.setPaymentStatus(PaymentStatus.COMPLETED);
                    orderRepo.save(order);
                }
                paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                paymentOrderRepo.save(paymentOrder);

                return true;
            }

            paymentOrder.setStatus(PaymentOrderStatus.FAILED);
            paymentOrderRepo.save(paymentOrder);
            return false;
        }
        return false;
    }

    @Override
    public PaymentLink createRazorpayPaymentLink(User user, Long Amount, Long orderId) throws RazorpayException {

        Long amount = Amount * 100;

        try {

            RazorpayClient razorpay = new RazorpayClient(apikey, apiSecret);

            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");

            // Create a JSON object with the customer details
            JSONObject customer = new JSONObject();
            customer.put("first_name",user.getFirstName());
            customer.put("last_name",user.getLastName());

            customer.put("email",user.getEmail());
            paymentLinkRequest.put("customer", customer);

            // Create a JSON object with the notification settings
            JSONObject notify = new JSONObject();
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);

            // Set the reminder settings
            paymentLinkRequest.put("reminder_enable",true);

            // Set the callback URL and method
            paymentLinkRequest.put("callback_url", "http://localhost:3000/payment-success/" +orderId);
            paymentLinkRequest.put("callback_method","get");

            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

            String paymentLinkUrl = payment.get("shirt_url");
            String paymentLinkId = payment.get("id");

            System.out.println("payment ----- "+payment);
            return  payment;
        } catch (RazorpayException e){

            System.out.println("Error creating payment link: " + e.getMessage());
            throw new RazorpayException(e.getMessage());
        }

    }

    @Override
    public String createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException {

        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-success/"+orderId)
                .setCancelUrl("http://localhost:3000/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(amount*100)
                                .setProductData(SessionCreateParams
                                        .LineItem
                                        .PriceData
                                        .ProductData
                                        .builder()
                                        .setName("simply buy payment")
                                        .build()
                                ).build()
                        ).build()
                ).build();

        Session session = Session.create(params);

        System.out.println("session _____ " + session);

//        PaymentLinkResponse res = new PaymentLinkResponse();
//        res.setPayment_link_url(session.getUrl());

        return session.getUrl();
    }
}
