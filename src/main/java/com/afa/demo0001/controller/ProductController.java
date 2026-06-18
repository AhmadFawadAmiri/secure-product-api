package com.afa.demo0001.controller;

import com.afa.demo0001.dto.ProductDto;
import com.afa.demo0001.model.ProductCreateRequest;
import com.afa.demo0001.repository.CategoryRepository;
import com.afa.demo0001.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public List<ProductDto> getProducts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
    ){
        return productService.getAllProductsDto(pageNo, pageSize, sortBy);
    }

    @PostMapping
    public ProductDto create(@Valid @RequestBody ProductCreateRequest request){
        return productService.saveProductDto(request);
    }

    @PutMapping("{id}")
    public ProductDto update(@PathVariable Long id, @RequestBody ProductCreateRequest request){
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully!");
    }

}
