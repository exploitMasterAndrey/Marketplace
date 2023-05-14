package com.example.marketplace_backend.controller;

import com.example.marketplace_backend.dto.ProductDto;
import com.example.marketplace_backend.model.Product;
import com.example.marketplace_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Класс контроллера, обрабатывающий запросы, связанные с продуктами
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    /**Поле слоя бизнес логики */
    private final ProductService productService;

    /**
     * Метод получения всех продуктов
     * @return все продукты
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(){
        List<Product> allProducts = productService.findAllProducts();
        return ResponseEntity.ok(allProducts);
    }

    /**
     * Метод создания продукта
     * @param productDto
     * @return созданный продукт
     */
    @PostMapping("/createOrUpdate")
    public ResponseEntity<?> createOrUpdateProduct(@RequestBody ProductDto productDto){
        if (productDto.getId() != null) return ResponseEntity.ok(productService.updateProduct(productDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDto));
    }

    /**
     * Получение продукта по id
     * @param id
     * @return полученный продукт
     */
    @GetMapping("/single/id/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * Получение продукта по названию
     * @param title
     * @return полученный продукт
     */
    @GetMapping("/single/title/{title}")
    public ResponseEntity<?> getProductByTitle(@PathVariable String title){
        return ResponseEntity.ok(productService.getProductByTitle(title));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.ok(new DeleteResponse(id));
    }

    /**
     * Получение страницы с продуктами
     * @param offset - номер страницы
     * @param limit - ограничение страницы
     * @param categoryId - идентификатор категории
     * @param price_min - минимальная цена
     * @param price_max - максимальная цена
     * @param title - название
     * @return полученная страница
     */
    @GetMapping
    public ResponseEntity<?> getPageOfProducts(@RequestParam Integer offset, @RequestParam Integer limit,
                                               @RequestParam(required = false) Long categoryId,
                                               @RequestParam(required = false) Integer price_min,
                                               @RequestParam(required = false) Integer price_max,
                                               @RequestParam(required = false) String title){
        return ResponseEntity.ok(productService.getPageOfSelectedProducts(price_min, price_max, categoryId, title, offset, limit));
    }

    record DeleteResponse(Long id){}
}
