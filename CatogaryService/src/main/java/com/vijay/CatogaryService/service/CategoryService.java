package com.vijay.CatogaryService.service;

import com.vijay.CatogaryService.model.CategoryDto;
import com.vijay.CatogaryService.model.PageableResponse;

public interface CategoryService {
    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto, String categoryId);

    //delete
    void delete(String categoryId);

    //get single category detail
    CategoryDto get(String categoryId);

    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);


}
