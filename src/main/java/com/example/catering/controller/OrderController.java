package com.example.catering.controller;

import com.example.catering.dto.UserDto;
import com.example.catering.entity.User;
import com.example.catering.service.MealService;
import com.example.catering.entity.Meal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import com.example.catering.dto.OrderDto;
import com.example.catering.entity.Order;
import com.example.catering.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import com.example.catering.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class OrderController {
    private OrderService orderService;
    private MealService mealService;
    private UserService userService;
    public OrderController(OrderService orderService, MealService mealService, UserService userService) {
        this.orderService = orderService;
        this.mealService = mealService;
        this.userService = userService;
    }

    @GetMapping
    public String showCart(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "cart";
    }

    @GetMapping("/add")
    public String showAddToCartForm(Model model) {
        OrderDto orderDto = new OrderDto();
        model.addAttribute("order", orderDto);
        return "add_to_cart";
    }

    @PostMapping("/add/save")
    public String addToCart(@Valid @ModelAttribute("order") OrderDto orderDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("order", orderDto);
            return "add_to_cart";
        }

        orderService.saveOrder(orderDto);
        return "redirect:/cart";
    }

    @GetMapping("/add/fromcart/{mealId}")
    public String addToCart(@PathVariable Long mealId) {
        // Pobierz posiłek na podstawie mealId
        Meal meal = mealService.getMealById(mealId);
        if (meal != null) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            User user = userService.findUserByEmail(username);
            Long userId = user.getId();
            OrderDto orderDto = new OrderDto();
            orderDto.setMealId(meal.getId());
            orderDto.setUserId(userId);
            orderDto.setQuantity(1);
            orderDto.setPrice(meal.getPrice());
            orderService.saveOrder(orderDto);

            return "redirect:/cart";
        } else {
            return "redirect:/menu?error=meal_not_found";
        }
    }
    @GetMapping("/edit/{id}")
    public String showEditCartItemForm(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "edit_cart_item";
    }

    @PostMapping("/edit/save")
    public String editCartItem(@Valid @ModelAttribute("order") OrderDto orderDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("order", orderDto);
            return "edit_cart_item";
        }

        orderService.updateOrder(orderDto);
        return "redirect:/cart";
    }

    @GetMapping("/delete/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return "redirect:/cart";
    }
}
