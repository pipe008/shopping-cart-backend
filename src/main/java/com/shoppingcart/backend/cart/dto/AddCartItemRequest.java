package com.shoppingcart.backend.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddCartItemRequest {

    @NotBlank(message = "El productId es obligatorio")
    private String productId;

    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer quantity;
}