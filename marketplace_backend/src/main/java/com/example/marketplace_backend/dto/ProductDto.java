package com.example.marketplace_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс модели передаваемых данных продукта
 */
@Data
@AllArgsConstructor
public class ProductDto {
    /** Идендификатор продукта*/
    private Long id;
    /** Описание продукта*/
    private String description;
    /** Картинка продукта*/
    private String image01;
    /** Картинка продукта*/
    private String image02;
    /** Картинка продукта*/
    private String image03;
    /** Цена продукта*/
    private Integer price;
    /** Заголовок продукта*/
    private String title;
    /** Количество покупок*/
    private Integer purchasedCnt;
    /** Категория продукта*/
    private String category;

    private Long sellerId;
}
