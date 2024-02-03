package com.vijay.ProductService.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String name;
    private long productId;
    private String categoryId;
    private long price;
    private long quantity;
    private CategoryDetails categoryDetails;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CategoryDetails {
        private String categoryId;
        private String title;
        private String description;
    }
}
