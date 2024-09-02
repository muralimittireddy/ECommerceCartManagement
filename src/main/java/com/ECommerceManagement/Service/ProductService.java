package com.ECommerceManagement.Service;
import com.ECommerceManagement.Repository.CategoryRepository;
import com.ECommerceManagement.Repository.ProductRepository;
import com.ECommerceManagement.Dto.ProductDTO;
import com.ECommerceManagement.Entities.Category;
import com.ECommerceManagement.Entities.Product;
import com.ECommerceManagement.Entities.User;
import com.ECommerceManagement.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());

        User seller = userRepository.findById(productDTO.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with username " + productDTO.getSellerId()));
        product.setSeller(seller);

        Category category = categoryRepository.findByCategoryId(productDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with name " + productDTO.getCategoryId()));
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return convertToProductDTO(savedProduct);
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
        return convertToProductDTO(product);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO convertToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setProductName(product.getProductName());
        productDTO.setPrice(product.getPrice());
        productDTO.setSellerId(product.getSeller().getId());
        productDTO.setCategoryId(product.getCategory().getCategoryId());
        return productDTO;
    }
}
