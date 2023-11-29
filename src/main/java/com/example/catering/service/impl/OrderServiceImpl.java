package com.example.catering.service.impl;

import com.example.catering.dto.OrderDto;
import com.example.catering.entity.Meal;
import com.example.catering.service.MealService;
import com.example.catering.entity.Order;
import com.example.catering.repository.OrderRepository;
import com.example.catering.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final MealService mealService;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, MealService mealService) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.mealService = mealService;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    @Override
    public void saveOrder(OrderDto orderDto) {
        Order order = modelMapper.map(orderDto, Order.class);
        orderRepository.save(order);
    }

    @Override
    public void updateOrder(OrderDto orderDto) {
        System.out.println("Received orderDto with id: " + orderDto.getId());
        Order existingOrder = getOrderById(orderDto.getId());
        System.out.println("Existing order: " + existingOrder);
        if (existingOrder != null) {


                existingOrder.setQuantity(orderDto.getQuantity());
                existingOrder.setPrice(orderDto.getPrice());

                // You can update other fields as needed

                System.out.println("Saving existing order: " + existingOrder);
                orderRepository.save(existingOrder);

        } else {
            System.out.println("Order not found with id: " + orderDto.getId());
            // For example:
            // throw new RuntimeException("Order not found with id: " + orderDto.getId());
        }
    }



    @Override
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }
    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
