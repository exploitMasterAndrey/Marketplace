package com.example.marketplace_backend.service;

import com.example.marketplace_backend.dto.OrderDto;
import com.example.marketplace_backend.dto.ProductDto;
import com.example.marketplace_backend.exceptions.UserNotFoundException;
import com.example.marketplace_backend.model.Order;
import com.example.marketplace_backend.model.Product;
import com.example.marketplace_backend.model.User;
import com.example.marketplace_backend.repository.OrderRepo;
import com.example.marketplace_backend.repository.ProductRepo;
import com.example.marketplace_backend.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;

    public OrderDto createOrder(OrderDto orderDto){
        User consumer = userRepo.findById(orderDto.getConsumerId()).orElseThrow(() -> new UserNotFoundException("No such user"));
        List<Product> products = orderDto.getProducts().stream().map(productDto -> productRepo.findById(productDto.getId()).get()).toList();
        Order order = new Order(
                orderDto.getConsumerPhone(),
                orderDto.getConsumerAddress(),
                consumer,
                products
        );
        Order saved = orderRepo.save(order);
        orderDto.setId(saved.getId());
        return orderDto;
    }

    public List<OrderDto> getAllUserOrders(Long userId){
        List<Order> orders = orderRepo.findAllByConsumer_Id(userId);

        return orders.stream().map(order -> new OrderDto(
                order.getId(),
                order.getConsumer().getId(),
                order.getConsumerPhone(),
                order.getConsumerAddress(),
                order.getProducts().stream().map(product ->
                        new ProductDto(
                                product.getId(),
                                product.getDescription(),
                                product.getImage01(),
                                product.getImage02(),
                                product.getImage03(),
                                product.getPrice(),
                                product.getTitle(),
                                product.getPurchasedCnt(),
                                product.getCategory().getName(),
                                product.getSeller().getId()
                        )
                        ).toList()
        )).toList();
    }
}
