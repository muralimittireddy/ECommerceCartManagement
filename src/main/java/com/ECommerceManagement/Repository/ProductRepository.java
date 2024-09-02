package com.ECommerceManagement.Repository;

import com.ECommerceManagement.Entities.Product;
import com.ECommerceManagement.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySeller(User seller);
}

