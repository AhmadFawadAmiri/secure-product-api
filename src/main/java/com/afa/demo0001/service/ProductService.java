package com.afa.demo0001.service;
import com.afa.demo0001.dto.ProductDto;
import com.afa.demo0001.model.Category;
import com.afa.demo0001.model.Product;
import com.afa.demo0001.repository.CategoryRepository;
import com.afa.demo0001.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductDto> getAllProductsDto(){
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory() != null ? product.getCategory().getName() : "Without Category"
        )).collect(Collectors.toList());
    }

    @Transactional
    public ProductDto saveProductDto(Product product){
        if(product.getCategory() != null && product.getCategory().getId() != null){
            Category category = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(() -> new IllegalArgumentException("this id is not found"));
            product.setCategory(category);
        }
        Product savedProduct = productRepository.save(product);
        //return productRepository.save(product);
        return new ProductDto(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getPrice(),
                savedProduct.getCategory() !=null ? savedProduct.getCategory().getName():"Without Cagegory"
        );
    }
}
