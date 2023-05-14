package com.example.marketplace_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Long consumerId;
    private String consumerPhone;
    private String consumerAddress;

    private List<ProductDto> products;
}
