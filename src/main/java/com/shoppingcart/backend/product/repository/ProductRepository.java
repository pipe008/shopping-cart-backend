package com.shoppingcart.backend.product.repository;

import com.shoppingcart.backend.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}