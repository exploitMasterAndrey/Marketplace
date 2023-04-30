package com.example.marketplace_backend.controller;


import com.example.marketplace_backend.model.Category;
import com.example.marketplace_backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Класс контроллера, обрабатывающий запросы, связанные с категориями
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {
    /**Поле слоя бизнес логики */
    private final CategoryService categoryService;

    /**
     * Метод получения всех категорий
     * @return все категории
     */
    @GetMapping
    public ResponseEntity<?> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    /**
     * Метод создания категории
     * @return категорию
     */
    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody Category category){
        return ResponseEntity.ok(categoryService.createCategory(category));
    }
}
