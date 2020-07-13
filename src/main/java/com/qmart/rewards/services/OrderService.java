package com.qmart.rewards.services;

import com.qmart.rewards.models.Order;
import com.qmart.rewards.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public Order getOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);

        return order.isPresent()?
                order.get() : null;
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }


}
