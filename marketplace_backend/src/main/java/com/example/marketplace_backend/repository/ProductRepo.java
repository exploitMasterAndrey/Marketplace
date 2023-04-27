package com.example.marketplace_backend.repository;

import com.example.marketplace_backend.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
//    Optional<Product> findPro(String title);
    List<Product> findProductByTitleContainingIgnoreCase(String title);

    List<Product> findProductsByCategory_Id(Long id, Pageable pageable);

    List<Product> findProductsByPriceGreaterThanAndCategory_Id(Integer price, Long id, Pageable pageable);
    List<Product> findProductsByPriceGreaterThanAndCategory_IdAndTitleContainingIgnoreCase(Integer price, Long id, String title, Pageable pageable);

    List<Product> findProductsByPriceLessThanAndCategory_Id(Integer price, Long id, Pageable pageable);
    List<Product> findProductsByPriceLessThanAndCategory_IdAndTitleContainingIgnoreCase(Integer price, Long id, String title, Pageable pageable);

    List<Product> findProductsByPriceBetweenAndCategory_Id(Integer price_min, Integer price_max, Long id, Pageable pageable);
    List<Product> findProductsByPriceBetweenAndCategory_IdAndTitleContainingIgnoreCase(Integer price_min, Integer price_max, Long id, String title, Pageable pageable);
}
