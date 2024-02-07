package com.vijay.ProductService.service;

import com.vijay.ProductService.model.ProductRequest;
import com.vijay.ProductService.model.ProductResponse;

import java.util.List;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
    void deleteProductById(long productId);

    List<ProductResponse> getAllProduct();

    void  updateProduct(long productId, ProductRequest productRequest);
}
