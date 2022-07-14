package com.codegym.thang.service.category;

import com.codegym.thang.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICategoryService {
    Page<Category> findAll(Pageable pageable);
    Optional<Category> findById(Long id);
    Category save(Category category);
    void remove(Long id);
}
