package com.ECommerceManagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long cartId;
    private Double totalAmount;
    private String userName;
    private Set<CartProductDTO> cartProducts;

    // Getters and Setters
}
