package tn.esprit.investia.services;

import tn.esprit.investia.entities.Order;
import tn.esprit.investia.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order placeOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order modifyOrder(Long orderId, Order updatedOrder) {
        Optional<Order> existingOrder = orderRepository.findById(orderId);
        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            order.setOrderType(updatedOrder.getOrderType());
          //  order.setQuantity(updatedOrder.getQuantity());
          //  order.setOrderStatus(updatedOrder.getOrderStatus());
            order.setOrderItem(updatedOrder.getOrderItem()); // if you want to update the item
            order.setStatus(updatedOrder.getStatus());

            return orderRepository.save(order);
        }
        return null;
    }

    public void cancelOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    public List<Order> getOrderHistory(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}