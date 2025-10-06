package com.sr.inventory_tracker.controller;

import com.sr.inventory_tracker.error.CategoryNotFoundException;
import com.sr.inventory_tracker.model.CategoryDTO;
import com.sr.inventory_tracker.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get Category by ID", description = "Retrieve a category by its ID", tags = {"Category"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public CategoryDTO getCategory(@PathVariable Long id) throws CategoryNotFoundException {
        log.info("Inside getCategory method of CategoryController - Getting category with ID: {}", id);
        return categoryService.getCategory(id);
    }


    @Operation(summary = "Get All Categories", description = "Retrieve a list of all categories", tags = {"Category"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of categories", content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        log.info("Inside getAllCategories method of CategoryController - Getting all categories");
        return categoryService.getAllCategories();
    }
}
