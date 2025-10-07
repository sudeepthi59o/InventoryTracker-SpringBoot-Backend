package com.sr.inventory_tracker.service;

import com.sr.inventory_tracker.error.CategoryNotFoundException;
import com.sr.inventory_tracker.model.Category;
import com.sr.inventory_tracker.model.CategoryDTO;
import com.sr.inventory_tracker.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDTO getCategory(Long id) throws CategoryNotFoundException {
        log.info("Inside getCategory method - Fetching category with ID: {}", id);

        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            log.error("Category with ID {} not found", id);
            throw new CategoryNotFoundException("Category with ID " + id + " not found");

        }
        return CategoryDTO
                .builder()
                .id(category.get().getId())
                .name(category.get().getName())
                .build();

    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        log.info("Inside getAllCategories method - Fetching all categories");

        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            log.warn("No categories found");
        }
        return fromEntityListToDTOList(categories);
    }

    public List<CategoryDTO> fromEntityListToDTOList(List<Category> categories) {
        log.info("Inside fromEntityListToDTOList method - Converting list of categories to list of category DTOs");

        List<CategoryDTO> categoryDTOs = new ArrayList<>();

        for (Category category : categories) {
            categoryDTOs.add(CategoryDTO
                    .builder()
                    .id(category.getId())
                    .name(category.getName())
                    .build());
        }

        return categoryDTOs;
    }


}
