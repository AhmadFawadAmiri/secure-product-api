package com.afa.demo0001;

import com.afa.demo0001.dto.ProductDto;
import com.afa.demo0001.model.Category;
import com.afa.demo0001.model.Product;
import com.afa.demo0001.repository.CategoryRepository;
import com.afa.demo0001.repository.ProductRepository;
import com.afa.demo0001.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveProductDto_Success(){

        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        Product product = new Product();
        product.setName("Test Phone");
        product.setPrice(500.0);
        product.setCategory(category);

        Product savedProduct = new Product();
        savedProduct.setId(100L);
        savedProduct.setName("Test Phone");
        savedProduct.setPrice(500.0);
        savedProduct.setCategory(category);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductDto resultDto = productService.saveProductDto(product);

        assertNotNull(resultDto);
        assertEquals(100L, resultDto.getId());
        assertEquals("Test Phone", resultDto.getName());
        assertEquals("Electronics", resultDto.getCategoryName());

        verify(productRepository, times(1)).save(any(Product.class));


    }
}
