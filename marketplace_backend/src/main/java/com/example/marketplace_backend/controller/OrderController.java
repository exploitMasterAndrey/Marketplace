package com.example.marketplace_backend.controller;

import com.example.marketplace_backend.dto.OrderDto;
import com.example.marketplace_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<?> getAllUserOrders(@PathVariable Long userId){
        List<OrderDto> orders = orderService.getAllUserOrders(userId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto){
        return ResponseEntity.ok(orderService.createOrder(orderDto));
    }
}
