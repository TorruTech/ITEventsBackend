package com.itevents.main.controllers;

import com.itevents.main.models.CategoryModel;
import com.itevents.main.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Category", description = "Endpoints de categorias")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Devuelve todas las categorias")
    @GetMapping
    public List<CategoryModel> getAll() {
        return categoryService.getAllCategories();
    }

    @Operation(summary = "Inserta una categoria")
    @PostMapping
    public CategoryModel create(@RequestBody CategoryModel category) {
        return categoryService.saveCategory(category);
    }

    @Operation(summary = "Actualiza una categoria por id")
    @PutMapping("/{id}")
    public Optional<CategoryModel> update(@PathVariable Long id, @RequestBody CategoryModel updated) {
        return categoryService.getById(id).map(category -> {
            category.setName(updated.getName());
            return categoryService.saveCategory(category);
        });
    }

    @Operation(summary = "Elimina una categoria por id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
