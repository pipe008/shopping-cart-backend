package com.shoppingcart.backend.order.controller;

import com.shoppingcart.backend.order.dto.CheckoutRequest;
import com.shoppingcart.backend.order.model.OrderDocument;
import com.shoppingcart.backend.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout/{userId}")
    public OrderDocument checkout(@PathVariable String userId, @Valid @RequestBody CheckoutRequest request) {
        return orderService.checkout(userId, request);
    }

    @GetMapping("/user/{userId}")
    public List<OrderDocument> findByUser(@PathVariable String userId) {
        return orderService.findByUser(userId);
    }
}