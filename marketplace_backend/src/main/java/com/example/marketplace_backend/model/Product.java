package com.example.marketplace_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Модель данных продукта
 */
@Entity
@Table
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String image01;
    private String image02;
    private String image03;
    private Integer price;
    private String title;
    private Integer purchasedCnt;

    @ManyToOne
    private User seller;

    @ManyToOne
    private Category category;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private List<Order> orders;

    public Product(String description, String image01, String image02, String image03, Integer price, String title, Category category, User seller) {
        this.description = description;
        this.image01 = image01;
        this.image02 = image02;
        this.image03 = image03;
        this.price = price;
        this.title = title;
        this.category = category;
        this.seller = seller;
    }
}
