package simply.Ecommerce.service;

import simply.Ecommerce.Enum.OrderStatus;
import simply.Ecommerce.exception.OrderException;
import simply.Ecommerce.model.*;

import java.util.List;
import java.util.Set;

public interface OrderService {

    Set<Order> createOrder(User user, Address shippingAddress, Cart cart);
    Order findOrderById(Long orderId) throws OrderException;
    List<Order> userOrderHistory(Long userId);
    Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws  OrderException;
    void deleteOrder(Long orderId) throws OrderException;
    Order cancelOrder(Long orderId, User user) throws OrderException;
   List<Order> getSellersOrder(Long sellerId) throws OrderException;
   OrderItem getOrderItemById(Long Id) throws Exception;
}
