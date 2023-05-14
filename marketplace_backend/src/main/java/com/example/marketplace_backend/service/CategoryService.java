package com.example.marketplace_backend.service;

import com.example.marketplace_backend.dto.CategoryDto;
import com.example.marketplace_backend.exceptions.CategoryNotFoundException;
import com.example.marketplace_backend.model.Category;
import com.example.marketplace_backend.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;

    public CategoryDto findCategoryByName(String name){
        Category byName = categoryRepo.findByName(name);
        return new CategoryDto(byName.getId(), byName.getName(), byName.getImage());
    }

    public void deleteCategory(Long id){
        categoryRepo.deleteById(id);
    }

    public List<CategoryDto> getAllCategories(){
        return categoryRepo.findAll().stream()
                .map(category -> new CategoryDto(category.getId(), category.getName(), category.getImage()))
                .collect(Collectors.toList());
    }

    public CategoryDto createCategory(CategoryDto categoryDto){
        Category saved = categoryRepo.save(new Category(categoryDto.getName(), categoryDto.getImage()));
        return new CategoryDto(saved.getId(), saved.getName(), saved.getImage());
    }

    public CategoryDto updateCategory(CategoryDto categoryDto){
        Optional<Category> categoryOptional = categoryRepo.findById(categoryDto.getId());
        Category updated = categoryOptional.orElseThrow(() -> new CategoryNotFoundException("No such category"));
        updated.setName(categoryDto.getName());
        updated.setImage(categoryDto.getImage());
        Category saved = categoryRepo.save(updated);
        return new CategoryDto(saved.getId(), saved.getName(), saved.getImage());
    }
}
