package com.example.marketplace_backend.service;

import com.example.marketplace_backend.model.Category;
import com.example.marketplace_backend.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;

    public Category createCategory(Category category){
        return categoryRepo.save(new Category(category.getImage(), category.getName()));
    }

    public Category findCategoryByName(String name){
        return categoryRepo.findByName(name);
    }

    public void deleteCategory(Long id){
        categoryRepo.deleteById(id);
    }

    public List<Category> getAllCategories(){
        return categoryRepo.findAll();
    }
}
