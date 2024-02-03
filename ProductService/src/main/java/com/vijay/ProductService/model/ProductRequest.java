package com.vijay.ProductService.model;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ProductRequest {

    private String categoryId;
    private String name;
    private long price;
    private long quantity;
}
