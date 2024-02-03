package com.vijay.CatogaryService.service;

import com.vijay.CatogaryService.entity.Category;
import com.vijay.CatogaryService.exception.ResourceNotFoundException;
import com.vijay.CatogaryService.helper.Helper;
import com.vijay.CatogaryService.model.CategoryDto;
import com.vijay.CatogaryService.model.PageableResponse;
import com.vijay.CatogaryService.repo.CategoryRepositoy;
import org.springframework.beans.factory.annotation.Autowired;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepositoy categoryRepositoy;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        Category category = mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepositoy.save(category);
        return mapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepositoy.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id !!"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = categoryRepositoy.save(category);
        return mapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        //get category of given id
        Category category = categoryRepositoy.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id !!"));
        categoryRepositoy.delete(category);
    }

    @Override
    public CategoryDto get(String categoryId) {
        Category category = categoryRepositoy.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id !!"));

        return mapper.map(category, CategoryDto.class);
    }
    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = categoryRepositoy.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        return pageableResponse;
    }
}
