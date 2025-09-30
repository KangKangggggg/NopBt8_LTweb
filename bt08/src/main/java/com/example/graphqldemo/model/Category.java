package com.example.graphqldemo.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String images;
    
    @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new HashSet<>();
    
    // Constructors
    public Category() {}
    
    public Category(String name, String images) {
        this.name = name;
        this.images = images;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }
    
    public Set<Product> getProducts() { return products; }
    public void setProducts(Set<Product> products) { this.products = products; }
}