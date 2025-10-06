package com.sr.inventory_tracker.controller;

import com.sr.inventory_tracker.error.CategoryNotFoundException;
import com.sr.inventory_tracker.model.CategoryDTO;
import com.sr.inventory_tracker.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/{id}")
    public CategoryDTO getCategory(@PathVariable Long id) throws CategoryNotFoundException {
        log.info("Inside getCategory method of CategoryController - Getting category with ID: {}", id);
        return categoryService.getCategory(id);
    }


    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        log.info("Inside getAllCategories method of CategoryController - Getting all categories");
        return categoryService.getAllCategories();
    }
}
