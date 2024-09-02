package com.ECommerceManagement.Controller;

import com.ECommerceManagement.Dto.CartProductDTO;
import com.ECommerceManagement.Service.CartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cartproducts")
public class CartProductController {

    @Autowired
    private CartProductService cartProductService;

    @PostMapping
    public ResponseEntity<CartProductDTO> addCartProduct(@RequestBody CartProductDTO cartProductDTO) {
        CartProductDTO createdCartProduct = cartProductService.addCartProduct(cartProductDTO);
        return new ResponseEntity<>(createdCartProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartProduct(@PathVariable Long id) {
        cartProductService.deleteCartProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

