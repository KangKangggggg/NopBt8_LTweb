package com.example.graphqldemo.controller;

import com.example.graphqldemo.model.Category;
import com.example.graphqldemo.model.Product;
import com.example.graphqldemo.model.User;
import com.example.graphqldemo.service.CategoryService;
import com.example.graphqldemo.service.ProductService;
import com.example.graphqldemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductGraphQLController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CategoryService categoryService;
    
    // Product Queries
    @QueryMapping
    public List<Product> products() {
        return productService.findAll();
    }
    
    @QueryMapping
    public List<Product> productsOrderByPrice() {
        return productService.findAllOrderByPriceAsc();
    }
    
    @QueryMapping
    public List<Product> productsByCategory(@Argument Long categoryId) {
        return productService.findByCategoryId(categoryId);
    }
    
    @QueryMapping
    public Optional<Product> productById(@Argument Long id) {
        return productService.findById(id);
    }
    
    // User Queries
    @QueryMapping
    public List<User> users() {
        return userService.findAll();
    }
    
    @QueryMapping
    public Optional<User> userById(@Argument Long id) {
        return userService.findById(id);
    }
    
    // Category Queries
    @QueryMapping
    public List<Category> categories() {
        return categoryService.findAll();
    }
    
    @QueryMapping
    public Optional<Category> categoryById(@Argument Long id) {
        return categoryService.findById(id);
    }
    
    // Product Mutations
    @MutationMapping
    public Product createProduct(@Argument String title, @Argument Integer quantity,
                               @Argument String description, @Argument Double price,
                               @Argument Long userId) {
        return productService.createProduct(title, quantity, description, price, userId);
    }
    
    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument String title,
                               @Argument Integer quantity, @Argument String description,
                               @Argument Double price) {
        Optional<Product> existingProduct = productService.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            if (title != null) product.setTitle(title);
            if (quantity != null) product.setQuantity(quantity);
            if (description != null) product.setDescription(description);
            if (price != null) product.setPrice(java.math.BigDecimal.valueOf(price));
            return productService.save(product);
        }
        throw new RuntimeException("Product not found with id: " + id);
    }
    
    @MutationMapping
    public Boolean deleteProduct(@Argument Long id) {
        try {
            productService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    // User Mutations
    @MutationMapping
    public User createUser(@Argument String fullname, @Argument String email,
                          @Argument String password, @Argument String phone) {
        User user = new User(fullname, email, password, phone);
        return userService.save(user);
    }
    
    @MutationMapping
    public User updateUser(@Argument Long id, @Argument String fullname,
                          @Argument String email, @Argument String phone) {
        Optional<User> existingUser = userService.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (fullname != null) user.setFullname(fullname);
            if (email != null) user.setEmail(email);
            if (phone != null) user.setPhone(phone);
            return userService.save(user);
        }
        throw new RuntimeException("User not found with id: " + id);
    }
    
    @MutationMapping
    public Boolean deleteUser(@Argument Long id) {
        try {
            userService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    // Category Mutations
    @MutationMapping
    public Category createCategory(@Argument String name, @Argument String images) {
        Category category = new Category(name, images);
        return categoryService.save(category);
    }
    
    @MutationMapping
    public Category updateCategory(@Argument Long id, @Argument String name,
                                 @Argument String images) {
        Optional<Category> existingCategory = categoryService.findById(id);
        if (existingCategory.isPresent()) {
            Category category = existingCategory.get();
            if (name != null) category.setName(name);
            if (images != null) category.setImages(images);
            return categoryService.save(category);
        }
        throw new RuntimeException("Category not found with id: " + id);
    }
    
    @MutationMapping
    public Boolean deleteCategory(@Argument Long id) {
        try {
            categoryService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}