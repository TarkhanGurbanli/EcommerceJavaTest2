package com.example.ecommerceapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryWithProductsDto {
    private CategoryDto category;
    private List<ProductDto> products;
}
