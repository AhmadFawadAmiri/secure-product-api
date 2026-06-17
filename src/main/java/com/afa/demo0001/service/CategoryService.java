package com.afa.demo0001.service;

import com.afa.demo0001.dto.CategoryDto;
import com.afa.demo0001.model.Category;
import com.afa.demo0001.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getAllCategoriesDto(){
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(category -> new CategoryDto(
                category.getId(),
                category.getName()
                )).collect(Collectors.toList());
    }

    public Category saveCategory(Category category){
        return categoryRepository.save(category);
    }
}
