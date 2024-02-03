package com.vijay.ProductService.service;

import com.vijay.ProductService.model.ProductRequest;
import com.vijay.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
