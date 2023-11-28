package com.example.ecommerceapi.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private String pictureUrl;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


}
