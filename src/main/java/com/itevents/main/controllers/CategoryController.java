package com.itevents.main.controllers;

import com.itevents.main.models.CategoryModel;
import com.itevents.main.services.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryModel> getAll() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    public CategoryModel create(@RequestBody CategoryModel category) {
        return categoryService.saveCategory(category);
    }

    @PutMapping("/{id}")
    public Optional<CategoryModel> update(@PathVariable Long id, @RequestBody CategoryModel updated) {
        return categoryService.getById(id).map(category -> {
            category.setName(updated.getName());
            return categoryService.saveCategory(category);
        });
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
