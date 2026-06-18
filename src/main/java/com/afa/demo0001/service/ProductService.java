package com.afa.demo0001.service;
import com.afa.demo0001.dto.ProductDto;
import com.afa.demo0001.model.Category;
import com.afa.demo0001.model.Product;
import com.afa.demo0001.model.ProductCreateRequest;
import com.afa.demo0001.model.Tag;
import com.afa.demo0001.repository.CategoryRepository;
import com.afa.demo0001.repository.ProductRepository;
import com.afa.demo0001.repository.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, TagRepository tagRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    public List<ProductDto> getAllProductsDto(int pageNo, int pageSize, String sortBy){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());

        Page<Product> productPage = productRepository.findAll(pageable);

        List<Product> listOfProducts = productPage.getContent();

        return listOfProducts.stream()
                .map(product -> mapToDto(product))
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto saveProductDto(ProductCreateRequest request){
        Product product = new Product(request.getName(), request.getPrice());
        if(request.getCategoryId() != null){
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found wiht ID "+request.getCategoryId()));
            product.setCategory(category);
        }
        Set<Tag> productTags = new HashSet<>();
        if(request.getTags() != null){
            for (String tagName : request.getTags()){
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(()-> tagRepository.save(new Tag(tagName)));
                productTags.add(tag);
            }
        }
        product.setTags(productTags);
        Product savedProduct = productRepository.save(product);
        return new ProductDto(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getPrice(),
                savedProduct.getCategory() !=null ? savedProduct.getCategory().getName():"Without Category"
        );
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductCreateRequest request){

        Product existingProduct = productRepository
                .findById(id).orElseThrow(()->new IllegalArgumentException("Product not found with ID " + id));
        existingProduct.setName(request.getName());
        existingProduct.setPrice(request.getPrice());

        if(request.getCategoryId() != null){
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(()->new IllegalArgumentException("Category not found"));
            existingProduct.setCategory(category);
        }else{
            existingProduct.setCategory(null);
        }
        Set<Tag> productTags = new HashSet<>();
        if(request.getTags() != null){
            for(String tagName : request.getTags()){
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(()-> tagRepository.save(new Tag(tagName)));
                productTags.add(tag);
            }
        }
        existingProduct.setTags(productTags);

        Product updateProduct = productRepository.save(existingProduct);

        return new ProductDto(
                updateProduct.getId(),
                updateProduct.getName(),
                updateProduct.getPrice(),
                updateProduct.getCategory()!=null?updateProduct.getCategory().getName():"Without Category");
    }

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Product not found with ID " + id));
        productRepository.delete(product);
    }

    private ProductDto mapToDto(Product product){
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory() != null ? product.getCategory().getName():"Without Category"
        );
    }
}
