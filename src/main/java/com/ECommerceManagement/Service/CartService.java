package com.ECommerceManagement.Service;

import com.ECommerceManagement.Dto.CartDTO;
import com.ECommerceManagement.Dto.CartProductDTO;
import com.ECommerceManagement.Dto.ProductDTO;
import com.ECommerceManagement.Entities.Cart;
import com.ECommerceManagement.Entities.CartProduct;
import com.ECommerceManagement.Entities.Product;
import com.ECommerceManagement.Entities.User;
import com.ECommerceManagement.Repository.CartRepository;
import com.ECommerceManagement.Repository.ProductRepository;
import com.ECommerceManagement.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public CartDTO createCartForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalAmount(0.0);

        Cart savedCart = cartRepository.save(cart);
        return convertToCartDTO(savedCart);
    }

    public CartDTO addProductToCart(Long cartId, CartProductDTO cartProductDTO) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found with id " + cartId));

        Product product = productRepository.findById(cartProductDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + cartProductDTO.getProductId()));

        CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(cartProductDTO.getQuantity());

        cart.getCartProducts().add(cartProduct);
        cart.setTotalAmount(cart.getTotalAmount() + (product.getPrice() * cartProduct.getQuantity()));

        Cart updatedCart = cartRepository.save(cart);
        return convertToCartDTO(updatedCart);
    }

    public CartDTO getCartById(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found with id " + id));
        return convertToCartDTO(cart);
    }

    private CartDTO convertToCartDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cart.getCartId());
        cartDTO.setTotalAmount(cart.getTotalAmount());
        cartDTO.setUserName(cart.getUser().getUsername());
        cartDTO.setCartProducts(cart.getCartProducts().stream()
                .map(this::convertToCartProductDTO)
                .collect(Collectors.toSet()));
        return cartDTO;
    }

    private CartProductDTO convertToCartProductDTO(CartProduct cartProduct) {
        CartProductDTO cartProductDTO = new CartProductDTO();
        cartProductDTO.setCpid(cartProduct.getCpid());
        cartProductDTO.setQuantity(cartProduct.getQuantity());

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(cartProduct.getProduct().getProductId());
        productDTO.setProductName(cartProduct.getProduct().getProductName());
        productDTO.setPrice(cartProduct.getProduct().getPrice());

        cartProductDTO.setProductId(productDTO.getProductId());
        return cartProductDTO;
    }
}
