package com.example.marketplace_backend.service;

import com.example.marketplace_backend.dto.ProductDto;
import com.example.marketplace_backend.exceptions.ProductNotFoundException;
import com.example.marketplace_backend.model.Category;
import com.example.marketplace_backend.model.Product;
import com.example.marketplace_backend.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final CategoryService categoryService;

    public Product createProduct(ProductDto productDto){
        Category category = categoryService.findCategoryByName(productDto.getCategory());
        Product product = new Product(
                productDto.getDescription(),
                productDto.getImage01(),
                productDto.getImage02(),
                productDto.getImage03(),
                productDto.getPrice(),
                productDto.getTitle(),
                category
        );
        return productRepo.save(product);
    }

    public Product getProductById(Long id){
        return productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("No product with such id!"));
    }

    public List<Product> getProductsByCategoryId(Long id, Pageable pageable){
        return productRepo.findProductsByCategory_Id(id, pageable);
    }

//    private List<Product> getProductsWherePriceGreaterThanAndCategory(Integer price, Long id, Pageable pageable){
//        return productRepo.findProductsByPriceGreaterThanAndCategory_Id(price, id, pageable);
//    }

//    private List<Product> getProductsWherePriceLessThanAndCategory(Integer price, Long id, Pageable pageable){
//        return productRepo.findProductsByPriceLessThanAndCategory_Id(price, id, pageable);
//    }

//    private List<Product> getProductWherePriceBetweenAndCategory(Integer price_min, Integer price_max, Long category_id, Pageable pageable){
//        return productRepo.findProductsByPriceBetweenAndCategory_Id(price_min, price_max, category_id, pageable);
//    }

    public List<Product> getPageOfSelectedProducts(Integer price_min, Integer price_max, Long category_id, String title, Integer offset, Integer limit){
        Pageable page = PageRequest.of(offset, limit);

        if (category_id != null){
            if ((price_min != null && price_min != 0) && price_max == null)
                return (title == null || !title.equals("")) ? productRepo.findProductsByPriceGreaterThanAndCategory_Id(price_min, category_id, page)
                                      : productRepo.findProductsByPriceGreaterThanAndCategory_IdAndTitleContainingIgnoreCase(price_min, category_id, title, page);
            else if (price_min == null && price_max != null)
                return (title == null || !title.equals("")) ? productRepo.findProductsByPriceLessThanAndCategory_Id(price_max, category_id, page)
                                     : productRepo.findProductsByPriceLessThanAndCategory_IdAndTitleContainingIgnoreCase(price_max, category_id, title, page);
            else if (price_min != null && price_max != null)
                return (title == null || !title.equals("")) ? productRepo.findProductsByPriceBetweenAndCategory_Id(price_min, price_max, category_id, page)
                                     : productRepo.findProductsByPriceBetweenAndCategory_IdAndTitleContainingIgnoreCase(price_min, price_max, category_id, title, page);
            return getProductsByCategoryId(category_id, page);
        }

        return getPageOfProducts(page);
    }

    public List<Product> getProductByTitle(String title){
        return productRepo.findProductByTitleContainingIgnoreCase(title);
    }

    public List<Product> findAllProducts(){
        return productRepo.findAll();
    }

    public List<Product> getPageOfProducts(Pageable page){
        return productRepo.findAll(page).toList();
    }

//    public Product updateProduct(Product updates){
//        Optional<Product> product = productRepo.findById(updates.getId());
//        if (updates.getCategory())
//    }

}
