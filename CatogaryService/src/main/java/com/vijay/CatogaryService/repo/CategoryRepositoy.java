package com.vijay.CatogaryService.repo;

import com.vijay.CatogaryService.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepositoy extends JpaRepository<Category, String> {
}
