package com.afa.demo0001.controller;

import com.afa.demo0001.dto.CategoryDto;
import com.afa.demo0001.model.Category;
import com.afa.demo0001.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDto> getAll(){
        return categoryService.getAllCategoriesDto();
    }

    @PostMapping
    public CategoryDto create(@RequestBody Category category){
        Category savedCategory = categoryService.saveCategory(category);

        return new CategoryDto(savedCategory.getId(),savedCategory.getName());
    }
}
