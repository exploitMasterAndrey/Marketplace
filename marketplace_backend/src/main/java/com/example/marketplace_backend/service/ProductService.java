package com.example.marketplace_backend.service;

import com.example.marketplace_backend.dto.ProductDto;
import com.example.marketplace_backend.exceptions.ProductNotFoundException;
import com.example.marketplace_backend.exceptions.UserNotFoundException;
import com.example.marketplace_backend.model.Category;
import com.example.marketplace_backend.model.Product;
import com.example.marketplace_backend.model.User;
import com.example.marketplace_backend.repository.CategoryRepo;
import com.example.marketplace_backend.repository.ProductRepo;
import com.example.marketplace_backend.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    public Product createProduct(ProductDto productDto){
        Optional<User> sellerOpt = userRepo.findById(productDto.getSellerId());
        User seller = sellerOpt.orElseThrow(() -> new UserNotFoundException("Seller not found!"));

        Category category = categoryRepo.findByName(productDto.getCategory());

        Product product = new Product(
                productDto.getDescription(),
                productDto.getImage01(),
                productDto.getImage02(),
                productDto.getImage03(),
                productDto.getPrice(),
                productDto.getTitle(),
                category,
                seller
        );
        return productRepo.save(product);
    }

    public Product updateProduct(ProductDto productDto){
        Category category = categoryRepo.findByName(productDto.getCategory());
        Product product = productRepo.getById(productDto.getId());

        if (category != null) product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setImage01(productDto.getImage01());
        product.setImage02(productDto.getImage02());
        product.setImage03(productDto.getImage03());

        return productRepo.save(product);
    }

    public void delete(Long id){
        productRepo.deleteById(id);
    }

    public Product getProductById(Long id){
        return productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("No product with such id!"));
    }

    public List<Product> getProductsByCategoryId(Long id, Pageable pageable){
        return productRepo.findProductsByCategory_Id(id, pageable);
    }

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
}
