package com.example.ecommerceapi.service;

import com.example.ecommerceapi.models.CategoryDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity<CategoryDto> addCategory(CategoryDto categoryDto);
    ResponseEntity<CategoryDto> updateCategory (CategoryDto categoryDto, Integer categoryId);
    void deleteCategory (Integer categoryId);
    ResponseEntity<CategoryDto> getByIdCategory (Integer categoryId);
    ResponseEntity<List<CategoryDto>> getAllCategories ();
}
