package com.example.graphqldemo.service;

import com.example.graphqldemo.model.Product;
import com.example.graphqldemo.model.User;
import com.example.graphqldemo.repository.ProductRepository;
import com.example.graphqldemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
    public List<Product> findAllOrderByPriceAsc() {
        return productRepository.findAllOrderByPriceAsc();
    }
    
    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
    
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
    
    public Product save(Product product) {
        return productRepository.save(product);
    }
    
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
    
    public Product createProduct(String title, Integer quantity, String description, 
                               Double price, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Product product = new Product();
            product.setTitle(title);
            product.setQuantity(quantity);
            product.setDescription(description);
            product.setPrice(java.math.BigDecimal.valueOf(price));
            product.setUser(user.get());
            return productRepository.save(product);
        }
        throw new RuntimeException("User not found with id: " + userId);
    }
}