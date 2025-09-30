package com.example.graphqldemo.repository;

import com.example.graphqldemo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query("SELECT p FROM Product p ORDER BY p.price ASC")
    List<Product> findAllOrderByPriceAsc();
    
    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.id = :categoryId")
    List<Product> findByCategoryId(Long categoryId);
}