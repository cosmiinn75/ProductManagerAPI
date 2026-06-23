package com.example.spring_security.product;

import com.example.spring_security.dto.ProductRequest;
import com.example.spring_security.dto.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> findAll(){
        return productService.getAllProducts();
    }

    @PostMapping
    public ProductResponse postProduct(@Valid @RequestBody ProductRequest product ) {
         return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public void putProduct(@Valid  @RequestBody ProductRequest product, @PathVariable Long id){
        productService.putProduct(product,id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }
}
