package com.shoppingcart.backend.order.repository;

import com.shoppingcart.backend.order.model.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<OrderDocument, String> {
    List<OrderDocument> findByUserIdOrderByCreatedAtDesc(String userId);
}