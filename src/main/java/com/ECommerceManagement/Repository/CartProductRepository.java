package com.ECommerceManagement.Repository;

import com.ECommerceManagement.Entities.Cart;
import com.ECommerceManagement.Entities.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    List<CartProduct> findByCart(Cart cart);
}
