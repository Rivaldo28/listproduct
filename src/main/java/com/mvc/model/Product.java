package com.mvc.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products", schema = "technology")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, length = 50)
    private String status;
}
