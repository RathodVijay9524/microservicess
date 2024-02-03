package com.vijay.ProductService.service;

import com.vijay.ProductService.entity.Product;
import com.vijay.ProductService.exception.ProductServiceCustomException;
import com.vijay.ProductService.model.CategoryResponse;
import com.vijay.ProductService.model.ProductRequest;
import com.vijay.ProductService.model.ProductResponse;
import com.vijay.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.springframework.beans.BeanUtils.*;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding Product .. !!");
        Product product
                = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .categoryId(productRequest.getCategoryId())
                .build();
        productRepository.save(product);
        log.info("product Created..!!");

        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        Product product= productRepository.findById(productId).orElseThrow(
                ()-> new ProductServiceCustomException("Product with given Id will not Found", "PRODUCT NOT FOUND")
        );

        log.info("Invoking Category service to fetch the product for id: {}", product.getCategoryId());
        CategoryResponse categoryResponse
                = restTemplate.getForObject(
                "http://CATEGORY-SERVICE/categories/" + product.getCategoryId(),
                CategoryResponse.class
        );

        ProductResponse.CategoryDetails categoryDetails
                =ProductResponse.CategoryDetails.builder()
                .title(categoryResponse.getTitle())
                .description(categoryResponse.getDescription())
                .categoryId(categoryResponse.getCategoryId())
                .build();

        ProductResponse productResponse
                =ProductResponse.builder()
                .name(product.getName())
                .productId(product.getProductId())
                .price(product.getPrice())
                .categoryId(product.getCategoryId())
                .quantity(product.getQuantity())
                .categoryDetails(categoryDetails)
                .build();

        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reduce Quantity {} for Id: {} ", quantity, productId);
        Product product
                =productRepository.findById(productId)
                .orElseThrow(()-> new ProductServiceCustomException("Product with Given Id will not found","PRODUCT_NOT_FOUND"));
        if(product.getQuantity()<quantity){
            throw new ProductServiceCustomException("Product douse not have Sufficient Quantity","INSUFFICIENT_QUANTITY");
        }
        product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);
        log.info("Product Quantity updated successfully");
    }
}














