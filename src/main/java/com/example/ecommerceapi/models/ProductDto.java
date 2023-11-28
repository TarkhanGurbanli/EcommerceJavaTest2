package com.example.ecommerceapi.models;

import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String pictureUrl;
    private Integer quantity;
    private Integer categoryId;
}
