package com.afa.demo0001.controller;

import com.afa.demo0001.dto.ProductDto;
import com.afa.demo0001.model.Category;
import com.afa.demo0001.model.Product;
import com.afa.demo0001.repository.CategoryRepository;
import com.afa.demo0001.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    public ProductController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<ProductDto> getAll(){
        return productService.getAllProductsDto();
    }

    @PostMapping
    public ProductDto create(@Valid @RequestBody Product product){
        return productService.saveProductDto(product);
    }
}
