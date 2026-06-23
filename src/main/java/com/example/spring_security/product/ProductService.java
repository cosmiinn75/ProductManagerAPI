package com.example.spring_security.product;

import com.example.spring_security.dto.ProductRequest;
import com.example.spring_security.dto.ProductResponse;
import com.example.spring_security.exception.ProductAccessDeniedException;
import com.example.spring_security.exception.ProductNotFoundException;
import com.example.spring_security.exception.UserNotFoundException;
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

    public List<ProductResponse> getAllProducts(){

        return productRepository.findByUserUsername(getCurrentUsername())
                .stream()
                .map(this::fromProductToProductResponse)
                .toList();
    }

    public ProductResponse addProduct(ProductRequest productRequest) {
        User user = userRepository.findByUsername(getCurrentUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
        Product product = new Product();
        product.setUser(user);
        product.setPrice(productRequest.price());
        product.setName(productRequest.name());
       Product savedProduct = productRepository.save(product);
       return fromProductToProductResponse(savedProduct);
    }

    public void putProduct(ProductRequest product, Long id) {

        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product not found!");
        }
        Product current_product = optionalProduct.get();

            if(!current_product.getUser().getUsername().equals(getCurrentUsername())) {
                throw new ProductAccessDeniedException("You can't change this product");
            }
            current_product.setName(product.name());
            current_product.setPrice(product.price());
            productRepository.save(current_product);
    }

    public void deleteProduct(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product not found!");
        }
        Product current_product = optionalProduct.get();
        if(!current_product.getUser().getUsername().equals(getCurrentUsername())) {
            throw new ProductAccessDeniedException("You can't delete this product");
        }
        productRepository.delete(current_product);
    }

    private ProductResponse fromProductToProductResponse(Product product){
        return new ProductResponse( product.getId(),product.getName(), product.getPrice());
    }


}
