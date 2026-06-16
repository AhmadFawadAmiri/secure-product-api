package com.afa.demo0001.controller;

import com.afa.demo0001.model.Category;
import com.afa.demo0001.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAll(){
        return categoryService.getAllCategories();
    }

    @PostMapping
    public Category create(Category category){
        return categoryService.saveCategory(category);
    }
}
