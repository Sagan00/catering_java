package com.example.catering.service;

import com.example.catering.dto.OrderDto;
import com.example.catering.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    List<Order> getOrdersByUserId(Long userId);
    void saveOrder(OrderDto orderDto);
    void updateOrder(OrderDto orderDto);
    void deleteOrderById(Long id);
}

