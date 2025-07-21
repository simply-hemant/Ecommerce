package simply.Ecommerce.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import simply.Ecommerce.Enum.OrderStatus;
import simply.Ecommerce.Enum.PaymentStatus;
import simply.Ecommerce.exception.OrderException;
import simply.Ecommerce.model.*;
import simply.Ecommerce.repository.AddressRepo;
import simply.Ecommerce.repository.OrderItemRepo;
import simply.Ecommerce.repository.OrderRepo;
import simply.Ecommerce.repository.UserRepo;
import simply.Ecommerce.service.CartService;
import simply.Ecommerce.service.EmailService;
import simply.Ecommerce.service.OrderService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final AddressRepo addressRepo;
    private final OrderItemRepo orderItemRepo;

    @Override
    public Set<Order> createOrder(User user, Address shippingAddress, Cart cart) {

        if (!user.getAddresses().contains(shippingAddress)) {
            user.getAddresses().add(shippingAddress);
        }

        Address address = addressRepo.save(shippingAddress);


        Map<Long, List<CartItem>> itemBySeller = cart.getCartItems().stream()
                .collect(Collectors.groupingBy(item -> item.getProduct().getSeller().getId()));

        Set<Order> orders = new HashSet<>();

        //separate order for all sellers(for loop....)

        for (Map.Entry<Long, List<CartItem>> entry : itemBySeller.entrySet()) {
            Long sellerId = entry.getKey();
            List<CartItem> cartItems = entry.getValue();

            int totalOrderPrice = cartItems.stream()
                    .mapToInt(CartItem::getMrpPrice).sum();
            int totalItem = cartItems.stream().mapToInt(CartItem::getQuantity).sum();

            Order createdOrder = new Order();
            createdOrder.setUser(user);
            createdOrder.setSellerId(sellerId);
            createdOrder.setTotalMrpPrice(totalOrderPrice);
            createdOrder.setTotalSellingPrice(totalOrderPrice);
            createdOrder.setTotalItem(totalItem);
            createdOrder.setShippingAddress(shippingAddress);
            createdOrder.setOrderStatus(OrderStatus.PENDING);
            createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);

            Order savedOrder = orderRepo.save(createdOrder);
            orders.add(savedOrder);

            List<OrderItem> orderItems = new ArrayList<>();

            for (CartItem item : cartItems) {
                OrderItem orderItem = new OrderItem();

                orderItem.setOrder(savedOrder);
                orderItem.setMrpPrice(item.getMrpPrice());
                orderItem.setProduct(item.getProduct());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setSize(item.getSize());
                orderItem.setUserId(item.getUserId());
                orderItem.setSellingPrice(item.getSellingPrice());

                savedOrder.getOrderItems().add(orderItem);   //bi directional

                OrderItem createdOrderItem = orderItemRepo.save(orderItem);

                orderItems.add(createdOrderItem);
            }
        }

        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> opt = orderRepo.findById(orderId);

        if (opt.isPresent()) {
            return opt.get();
        }


        throw new OrderException("order not exist with id " + orderId);
    }

    @Override
    public List<Order> userOrderHistory(Long userId) {

        return orderRepo.findByUser_UserId(userId);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws OrderException {

        Order order = findOrderById(orderId);
        order.setOrderStatus(orderStatus);
        return orderRepo.save(order);
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {

        Order order = findOrderById(orderId);

        orderRepo.deleteById(orderId);
    }

    @Override
    public Order cancelOrder(Long orderId, User user) throws OrderException {

        Order order = this.findOrderById(orderId);

        if (user.getUserId() != order.getUser().getUserId()) {
            throw new OrderException("you can not perform this action " + orderId);
        }

        order.setOrderStatus(OrderStatus.CANCELLED);

        return orderRepo.save(order);

    }

    @Override
    public List<Order> getSellersOrder(Long sellerId) throws OrderException {
        return orderRepo.findBySellerId(sellerId);
    }

    @Override
    public OrderItem getOrderItemById(Long Id) throws Exception {
//        return orderRepo.findById(Id).orElseThrow(()->
//                new Exception("order item not exist...."));

        Optional<OrderItem> optionalOrder = orderItemRepo.findById(Id);
        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        } else {
            throw new Exception("Order item does not exist...");
        }

    }
}
