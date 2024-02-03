package com.vijay.ProductService.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;

    @Column(name="category_id")
    private String categoryId;

    @Column(name="PRODUCT_NAME")
    private String name;


    @Column(name="PRICE")
    private long price;

    @Column(name ="QUANTITY")
    private long quantity;


}
