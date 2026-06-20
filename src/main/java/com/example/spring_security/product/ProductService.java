package com.example.spring_security.product;

import com.example.spring_security.user.User;
import com.example.spring_security.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    private String getCurrentUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public List<Product> getAllProducts(){
        return productRepository.findByUserUsername(getCurrentUsername());
    }

    public void addProduct(Product product) {
        User user = userRepository.findByUsername(getCurrentUsername()).orElseThrow(() -> new RuntimeException("User not found"));

        product.setUser(user);
        productRepository.save(product);
    }

    public void putProduct(Product product, Long id) {

        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isEmpty()) {
            throw new RuntimeException("");
        }
        Product current_product = optionalProduct.get();

            if(!current_product.getUser().getUsername().equals(getCurrentUsername())) {
                throw new RuntimeException("");
            }
            current_product.setName(product.getName());
            current_product.setPrice(product.getPrice());
            productRepository.save(current_product);
    }

    public void deleteProduct(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isEmpty()) {
            throw new RuntimeException("");
        }
        Product current_product = optionalProduct.get();
        if(!current_product.getUser().getUsername().equals(getCurrentUsername())) {
            throw new RuntimeException("");
        }
        productRepository.delete(current_product);
    }




}
