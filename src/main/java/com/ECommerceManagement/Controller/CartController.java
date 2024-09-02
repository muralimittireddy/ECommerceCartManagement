package com.ECommerceManagement.Controller;

import com.ECommerceManagement.Dto.CartDTO;
import com.ECommerceManagement.Dto.CartProductDTO;
import com.ECommerceManagement.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<CartDTO> createCart(@PathVariable Long userId) {
        CartDTO createdCart = cartService.createCartForUser(userId);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    @PostMapping("/{cartId}/products")
    public ResponseEntity<CartDTO> addProductToCart(
            @PathVariable Long cartId,
            @RequestBody CartProductDTO cartProductDTO) {
        CartDTO updatedCart = cartService.addProductToCart(cartId, cartProductDTO);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long id) {
        CartDTO cart = cartService.getCartById(id);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
