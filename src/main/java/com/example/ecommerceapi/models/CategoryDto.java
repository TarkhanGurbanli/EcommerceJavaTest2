package com.example.ecommerceapi.models;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class CategoryDto {
    private Integer id;
    private String name;
}
