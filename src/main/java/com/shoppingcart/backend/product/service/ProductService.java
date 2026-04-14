package com.shoppingcart.backend.product.service;

import com.shoppingcart.backend.common.exception.NotFoundException;
import com.shoppingcart.backend.product.dto.ProductRequest;
import com.shoppingcart.backend.product.model.Product;
import com.shoppingcart.backend.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id: " + id));
    }

    public Product create(ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(request.getCategory())
                .image(request.getImage())
                .available(request.getAvailable() != null ? request.getAvailable() : request.getStock() > 0)
                .build();

        return productRepository.save(product);
    }

    public Product update(String id, ProductRequest request) {
        Product product = findById(id);

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(request.getCategory());
        product.setImage(request.getImage());
        product.setAvailable(request.getAvailable() != null ? request.getAvailable() : request.getStock() > 0);

        return productRepository.save(product);
    }

    public void delete(String id) {
        Product product = findById(id);
        productRepository.delete(product);
    }
}