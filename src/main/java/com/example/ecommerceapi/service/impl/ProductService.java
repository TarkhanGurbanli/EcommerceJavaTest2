package com.example.ecommerceapi.service.impl;

import com.example.ecommerceapi.entities.Category;
import com.example.ecommerceapi.entities.Product;
import com.example.ecommerceapi.models.CategoryDto;
import com.example.ecommerceapi.models.CategoryWithProductsDto;
import com.example.ecommerceapi.models.ProductDto;
import com.example.ecommerceapi.repository.CategoryRepository;
import com.example.ecommerceapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService implements com.example.ecommerceapi.service.ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<ProductDto> addProduct(ProductDto productDto) {
        if (productDto == null || productDto.getName().trim().isEmpty() || productDto.getCategoryId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
            if (optionalCategory.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Product product = modelMapper.map(productDto, Product.class);
            product.setCategory(optionalCategory.get());

            Product savedProduct = productRepository.save(product);
            return new ResponseEntity<>(modelMapper.map(savedProduct, ProductDto.class), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<ProductDto> updateProduct(ProductDto productDto, Integer productId) {
        try {
            if (productDto == null || productRepository.existsByName(productDto.getName())) {
                return ResponseEntity.badRequest().body(productDto);
            }

            Optional<Product> optionalProduct = productRepository.findById(productId);

            if (optionalProduct.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Product existingProduct = optionalProduct.get();

            // Check if the updated name already exists in the category
            if (!existingProduct.getName().equals(productDto.getName()) && productRepository.existsByName(productDto.getName())) {
                return ResponseEntity.badRequest().body(productDto);
            }

            // Update all fields of the existing product with the values from the DTO
            existingProduct.setName(productDto.getName());
            existingProduct.setPictureUrl(productDto.getPictureUrl());
            existingProduct.setQuantity(productDto.getQuantity());
            existingProduct.setPrice(productDto.getPrice());

            // Retrieve and set the category
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
            existingProduct.setCategory(category);

            // Save the updated product
            Product updatedProduct = productRepository.save(existingProduct);

            // Map and return the updated product
            return ResponseEntity.ok(modelMapper.map(updatedProduct, ProductDto.class));
        }catch (Exception er) {
            return new ResponseEntity<ProductDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Override
    public void deleteProduct(Integer productId) {

    }

    @Override
    public ResponseEntity<ProductDto> getByIdProduct(Integer productId) {
        return null;
    }

    @Override
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return null;
    }

    @Override
    public ResponseEntity<List<CategoryWithProductsDto>> getAllCategoriesWithProducts() {
        try {
            List<CategoryWithProductsDto> categoriesWithProducts = new ArrayList<>();

            List<Category> categories = categoryRepository.findAll();
            for (Category category : categories) {
                List<ProductDto> productsInCategory = getProductDtosInCategory(category.getId());
                CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
                CategoryWithProductsDto categoryWithProductsDto = new CategoryWithProductsDto(categoryDto, productsInCategory);
                categoriesWithProducts.add(categoryWithProductsDto);
            }

            return ResponseEntity.ok(categoriesWithProducts);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private List<ProductDto> getProductDtosInCategory(Integer categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

}
