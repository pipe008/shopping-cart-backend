package com.shoppingcart.backend.order.service;

import com.shoppingcart.backend.cart.model.Cart;
import com.shoppingcart.backend.cart.model.CartItem;
import com.shoppingcart.backend.cart.repository.CartRepository;
import com.shoppingcart.backend.common.exception.NotFoundException;
import com.shoppingcart.backend.order.dto.CheckoutRequest;
import com.shoppingcart.backend.order.model.OrderDocument;
import com.shoppingcart.backend.order.model.OrderItem;
import com.shoppingcart.backend.order.repository.OrderRepository;
import com.shoppingcart.backend.product.model.Product;
import com.shoppingcart.backend.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public OrderDocument checkout(String userId, CheckoutRequest request) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("No existe carrito para el usuario"));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new NotFoundException("Producto no encontrado: " + cartItem.getProductId()));

            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + product.getName());
            }

            product.setStock(product.getStock() - cartItem.getQuantity());
            product.setAvailable(product.getStock() > 0);
            productRepository.save(product);

            orderItems.add(
                    OrderItem.builder()
                            .productId(cartItem.getProductId())
                            .name(cartItem.getName())
                            .price(cartItem.getPrice())
                            .quantity(cartItem.getQuantity())
                            .build()
            );
        }

        OrderDocument order = OrderDocument.builder()
                .userId(userId)
                .customerName(request.getCustomerName())
                .email(request.getEmail())
                .address(request.getAddress())
                .items(orderItems)
                .total(cart.getTotal())
                .status("CREATED")
                .createdAt(LocalDateTime.now())
                .build();

        OrderDocument savedOrder = orderRepository.save(order);

        cart.setItems(new ArrayList<>());
        cart.setTotal(0.0);
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);

        return savedOrder;
    }

    public List<OrderDocument> findByUser(String userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}