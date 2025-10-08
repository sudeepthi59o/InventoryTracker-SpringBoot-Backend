package com.sr.inventory_tracker.service;

import com.sr.inventory_tracker.error.CategoryNotFoundException;
import com.sr.inventory_tracker.DTO.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO getCategory(Long id) throws CategoryNotFoundException;

    List<CategoryDTO> getAllCategories();

}
