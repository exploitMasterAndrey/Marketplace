package com.example.marketplace_backend.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String description;
    private String image01;
    private String image02;
    private String image03;
    private Integer price;
    private String title;
    private Integer purchasedCnt;
    private String category;
}
