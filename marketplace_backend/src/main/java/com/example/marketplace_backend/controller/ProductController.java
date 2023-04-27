package com.example.marketplace_backend.controller;

import com.example.marketplace_backend.dto.ProductDto;
import com.example.marketplace_backend.model.Product;
import com.example.marketplace_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(){
        List<Product> allProducts = productService.findAllProducts();
        return ResponseEntity.ok(allProducts);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto){
        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    @GetMapping("/single/id/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/single/title/{title}")
    public ResponseEntity<?> getProductByTitle(@PathVariable String title){
        return ResponseEntity.ok(productService.getProductByTitle(title));
    }

    @GetMapping
    public ResponseEntity<?> getPageOfProducts(@RequestParam Integer offset, @RequestParam Integer limit,
                                               @RequestParam(required = false) Long categoryId,
                                               @RequestParam(required = false) Integer price_min,
                                               @RequestParam(required = false) Integer price_max,
                                               @RequestParam(required = false) String title){
        return ResponseEntity.ok(productService.getPageOfSelectedProducts(price_min, price_max, categoryId, title, offset, limit));
    }
}
