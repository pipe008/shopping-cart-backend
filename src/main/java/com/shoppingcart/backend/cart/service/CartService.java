package com.shoppingcart.backend.cart.service;

import com.shoppingcart.backend.cart.dto.AddCartItemRequest;
import com.shoppingcart.backend.cart.dto.QuantityRequest;
import com.shoppingcart.backend.cart.model.Cart;
import com.shoppingcart.backend.cart.model.CartItem;
import com.shoppingcart.backend.cart.repository.CartRepository;
import com.shoppingcart.backend.common.exception.NotFoundException;
import com.shoppingcart.backend.product.model.Product;
import com.shoppingcart.backend.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public Cart getCartByUser(String userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(
                        Cart.builder()
                                .userId(userId)
                                .items(new ArrayList<>())
                                .total(0.0)
                                .updatedAt(LocalDateTime.now())
                                .build()
                ));
    }

    public Cart addItem(String userId, AddCartItemRequest request) {
        Cart cart = getCartByUser(userId);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        if (Boolean.FALSE.equals(product.getAvailable()) || product.getStock() <= 0) {
            throw new RuntimeException("El producto no está disponible");
        }

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        int requestedQty = request.getQuantity();

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + requestedQty;

            if (newQuantity > product.getStock()) {
                throw new RuntimeException("La cantidad supera el stock disponible");
            }

            existingItem.setQuantity(newQuantity);
        } else {
            if (requestedQty > product.getStock()) {
                throw new RuntimeException("La cantidad supera el stock disponible");
            }

            cart.getItems().add(
                    CartItem.builder()
                            .productId(product.getId())
                            .name(product.getName())
                            .price(product.getPrice())
                            .image(product.getImage())
                            .quantity(requestedQty)
                            .stock(product.getStock())
                            .build()
            );
        }

        recalculate(cart);
        return cartRepository.save(cart);
    }

    public Cart updateItem(String userId, String productId, QuantityRequest request) {
        Cart cart = getCartByUser(userId);

        CartItem item = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Producto no encontrado en el carrito"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        if (request.getQuantity() > product.getStock()) {
            throw new RuntimeException("La cantidad supera el stock disponible");
        }

        item.setQuantity(request.getQuantity());
        item.setStock(product.getStock());

        recalculate(cart);
        return cartRepository.save(cart);
    }

    public Cart removeItem(String userId, String productId) {
        Cart cart = getCartByUser(userId);

        cart.getItems().removeIf(item -> item.getProductId().equals(productId));

        recalculate(cart);
        return cartRepository.save(cart);
    }

    public Cart clearCart(String userId) {
        Cart cart = getCartByUser(userId);
        cart.setItems(new ArrayList<>());
        cart.setTotal(0.0);
        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    private void recalculate(Cart cart) {
        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        cart.setTotal(total);
        cart.setUpdatedAt(LocalDateTime.now());
    }
}