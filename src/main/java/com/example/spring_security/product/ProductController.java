package com.example.spring_security.product;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public List<Product> findAll(){
        return productService.getAllProducts();
    }

    @PostMapping("")
    public void postProduct(@RequestBody Product product ) {
         productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public void putProduct(@RequestBody Product product, @PathVariable Long id){
        productService.putProduct(product,id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }
}
