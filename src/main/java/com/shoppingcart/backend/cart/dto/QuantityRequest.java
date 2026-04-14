package com.shoppingcart.backend.cart.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class QuantityRequest {

    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer quantity;
}