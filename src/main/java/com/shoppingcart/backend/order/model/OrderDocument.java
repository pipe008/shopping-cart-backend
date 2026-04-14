package com.shoppingcart.backend.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDocument {

    @Id
    private String id;

    private String userId;
    private String customerName;
    private String email;
    private String address;
    private List<OrderItem> items;
    private Double total;
    private String status;
    private LocalDateTime createdAt;
}