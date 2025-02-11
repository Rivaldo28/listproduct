package com.mvc.dto;

import lombok.Data;

@Data
public class ProductResponse {
    private String name;
    private String description;
    private float price;
    private int quantity;
    private String status;
    private String message;
}
