package com.vijay.ProductService.controller;

import com.vijay.ProductService.model.ErrorResponse;
import com.vijay.ProductService.model.ProductRequest;
import com.vijay.ProductService.model.ProductResponse;
import com.vijay.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest){
        long productId= productService.addProduct(productRequest);
        return new ResponseEntity<>(productId,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId){
        ProductResponse productById= productService.getProductById(productId);
        return new ResponseEntity<>(productById, HttpStatus.OK);
    }
    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId, @RequestParam long quantity){
        productService.reduceQuantity(productId,quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorResponse> deleteProductById(@PathVariable("id") long ProductId){
        productService.deleteProductById(ProductId);
        ErrorResponse errorResponse=ErrorResponse.builder()
                .errorMessage("Product is deleted Successfully !!")
                .errorCode("PRODUCT DELETED")
                .build();
        return new ResponseEntity<>(errorResponse,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProduct(){
        List<ProductResponse> allProduct = productService.getAllProduct();
        return new ResponseEntity<List<ProductResponse>>(allProduct,HttpStatus.ACCEPTED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ErrorResponse> updateProduct(@PathVariable("id") long productId, @RequestBody ProductRequest productRequest){
        productService.updateProduct(productId,productRequest);
        ErrorResponse errorResponse=ErrorResponse.builder()
                .errorMessage("Product is Updated Successfully !!")
                .errorCode("SUCCESS !!")
                .build();
        return new ResponseEntity<>(errorResponse,HttpStatus.OK);
    }

}
