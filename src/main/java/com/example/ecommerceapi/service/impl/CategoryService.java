package com.example.ecommerceapi.service.impl;

import com.example.ecommerceapi.entities.Category;
import com.example.ecommerceapi.models.CategoryDto;
import com.example.ecommerceapi.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService implements com.example.ecommerceapi.service.CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<CategoryDto> addCategory(CategoryDto categoryDto) {
        if (categoryDto == null || categoryDto.getName() == null || categoryDto.getName().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (categoryRepository.existsByName(categoryDto.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Category category = modelMapper.map(categoryDto, Category.class);
            Category savedCategory = categoryRepository.save(category);
            return new ResponseEntity<>(modelMapper.map(savedCategory, CategoryDto.class), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<CategoryDto> updateCategory(CategoryDto categoryDto, Integer categoryId) {
        if (categoryDto == null || categoryDto.getName() == null || categoryDto.getName().trim().isEmpty() || categoryId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if (optionalCategory.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Category existingCategory = optionalCategory.get();

        if (!existingCategory.getName().equals(categoryDto.getName()) && categoryRepository.existsByName(categoryDto.getName())) {
            return ResponseEntity.notFound().build();
        }

        existingCategory.setName(categoryDto.getName());

        try {
            Category updatedCategory = categoryRepository.save(existingCategory);
            return ResponseEntity.ok(modelMapper.map(updatedCategory, CategoryDto.class));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CategoryDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public void deleteCategory(Integer categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            categoryRepository.delete(category);
        } else {
            throw new RuntimeException("Category is not found");
        }
    }

    @Override
    public ResponseEntity<CategoryDto> getByIdCategory(Integer categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
            return ResponseEntity.ok(categoryDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Override
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty())
            return ResponseEntity.notFound().build();

        List<CategoryDto> categoryDtos = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(categoryDtos);
    }

}
