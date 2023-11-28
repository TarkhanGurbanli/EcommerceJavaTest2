package com.example.ecommerceapi.controllers;

import com.example.ecommerceapi.service.CategoryService;
import com.example.ecommerceapi.models.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    private final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        ResponseEntity<CategoryDto> responseEntity = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId) {
        ResponseEntity<CategoryDto> updatedCategory = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory.getBody(), updatedCategory.getStatusCode());
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getCategory/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId) {
        ResponseEntity<CategoryDto> responseEntity = categoryService.getByIdCategory(categoryId);
        if (responseEntity.getBody() != null)
            return responseEntity;
        else
            return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        ResponseEntity<List<CategoryDto>> responseEntity = categoryService.getAllCategories();

        if (responseEntity.getBody() != null)
            return responseEntity;
        else
            return ResponseEntity.notFound().build();
    }

}
