package com.shoppingcart.backend.cart.controller;

import com.shoppingcart.backend.cart.dto.AddCartItemRequest;
import com.shoppingcart.backend.cart.dto.QuantityRequest;
import com.shoppingcart.backend.cart.model.Cart;
import com.shoppingcart.backend.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable String userId) {
        return cartService.getCartByUser(userId);
    }

    @PostMapping("/{userId}/items")
    public Cart addItem(@PathVariable String userId, @Valid @RequestBody AddCartItemRequest request) {
        return cartService.addItem(userId, request);
    }

    @PutMapping("/{userId}/items/{productId}")
    public Cart updateItem(
            @PathVariable String userId,
            @PathVariable String productId,
            @Valid @RequestBody QuantityRequest request
    ) {
        return cartService.updateItem(userId, productId, request);
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public Cart removeItem(@PathVariable String userId, @PathVariable String productId) {
        return cartService.removeItem(userId, productId);
    }

    @DeleteMapping("/{userId}/clear")
    public Cart clearCart(@PathVariable String userId) {
        return cartService.clearCart(userId);
    }
}