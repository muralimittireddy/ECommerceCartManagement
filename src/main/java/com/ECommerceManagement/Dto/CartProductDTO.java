package com.ECommerceManagement.Dto;

import com.ECommerceManagement.Entities.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProductDTO {
    private Long cpid;
    private CartDTO cart;
    private ProductDTO product;
    private Integer quantity;

    // Getters and Setters
}
