package com.ECommerceManagement.Service;

import com.ECommerceManagement.Dto.CartDTO;
import com.ECommerceManagement.Dto.CartProductDTO;
import com.ECommerceManagement.Dto.ProductDTO;
import com.ECommerceManagement.Entities.Cart;
import com.ECommerceManagement.Entities.CartProduct;
import com.ECommerceManagement.Entities.Product;
import com.ECommerceManagement.Repository.CartProductRepository;
import com.ECommerceManagement.Repository.CartRepository;
import com.ECommerceManagement.Repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartProductService {

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public CartProductDTO addCartProduct(CartProductDTO cartProductDTO) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setQuantity(cartProductDTO.getQuantity());

        // Fetch the Cart entity
        Cart cart = cartRepository.findById(cartProductDTO.getCart().getCartId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found with id " + cartProductDTO.getCart().getCartId()));

        // Fetch the Product entity
        Product product = productRepository.findById(cartProductDTO.getProduct().getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + cartProductDTO.getProduct().getProductId()));

        cartProduct.setCart(cart);
        cartProduct.setProduct(product);

        CartProduct savedCartProduct = cartProductRepository.save(cartProduct);
        return convertToCartProductDTO(savedCartProduct);
    }

    public void deleteCartProduct(Long id) {
        CartProduct cartProduct = cartProductRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CartProduct not found with id " + id));
        cartProductRepository.delete(cartProduct);
    }

    private CartProductDTO convertToCartProductDTO(CartProduct cartProduct) {
        CartProductDTO cartProductDTO = new CartProductDTO();
        cartProductDTO.setCpid(cartProduct.getCpid());
        cartProductDTO.setQuantity(cartProduct.getQuantity());

        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cartProduct.getCart().getCartId());
        cartProductDTO.setCart(cartDTO);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(cartProduct.getProduct().getProductId());
        productDTO.setProductName(cartProduct.getProduct().getProductName());
        productDTO.setPrice(cartProduct.getProduct().getPrice());

        cartProductDTO.setProduct(productDTO);

        return cartProductDTO;
    }
}