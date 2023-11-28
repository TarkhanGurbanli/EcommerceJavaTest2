package com.example.ecommerceapi.controllers;

import com.example.ecommerceapi.service.ProductService;
import com.example.ecommerceapi.models.CategoryWithProductsDto;
import com.example.ecommerceapi.models.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//constructor avtomatik yaradir ve kodu daha qisa edir
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<ProductDto> addProduct(ProductDto productDto){
        ResponseEntity<ProductDto> result = productService.addProduct(productDto);
        return new ResponseEntity<>(result.getBody(), result.getStatusCode());
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(ProductDto productDto,Integer productId){
        ResponseEntity<ProductDto> result = productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(result.getBody(), result.getStatusCode());
    }

    @GetMapping("/getAllCategoriesWithProducts")
    public ResponseEntity<List<CategoryWithProductsDto>> getAllCategoriesWithProducts() {
        ResponseEntity<List<CategoryWithProductsDto>> result = productService.getAllCategoriesWithProducts();

        if (result.getBody() != null) {
            return result;
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
