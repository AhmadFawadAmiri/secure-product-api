package com.afa.demo0001.controller;

import com.afa.demo0001.model.Product;
import com.afa.demo0001.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class PoductController {

    private final ProductService productService;

    public PoductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAll(){
        return productService.getAllProducts();
    }

    @PostMapping
    public Product create(@Valid @RequestBody Product product){
        return productService.saveProduct(product);
    }
}
