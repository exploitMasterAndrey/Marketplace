package com.example.marketplace_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "ord")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String consumerPhone;
    private String consumerAddress;

    @ManyToOne
    private User consumer;

    @ManyToMany
    @JoinTable(
            name = "product_order",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "ord_id")
    )
    private List<Product> products;

    public Order(String consumerPhone, String consumerAddress, User consumer, List<Product> products) {
        this.consumerPhone = consumerPhone;
        this.consumerAddress = consumerAddress;
        this.consumer = consumer;
        this.products = products;
    }
}
