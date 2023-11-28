package com.example.ecommerceapi.service;

import com.example.ecommerceapi.models.CategoryWithProductsDto;
import com.example.ecommerceapi.models.ProductDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<ProductDto> addProduct(ProductDto productDto);
    ResponseEntity<ProductDto> updateProduct(ProductDto productDto, Integer productId);
    void deleteProduct(Integer productId);
    ResponseEntity<ProductDto> getByIdProduct(Integer productId);
    ResponseEntity<List<ProductDto>> getAllProducts();
    ResponseEntity<List<CategoryWithProductsDto>> getAllCategoriesWithProducts();
}
