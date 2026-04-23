package br.com.fiap.checkpoint1.controller;

import br.com.fiap.checkpoint1.model.OrderModel;
import br.com.fiap.checkpoint1.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderModel> createOrder(@Valid @RequestBody OrderModel order) {
        try {
            OrderModel createdOrder = orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderModel>> readAllOrders() {
        List<OrderModel> orders = orderService.readAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{code}")
    public ResponseEntity<OrderModel> readOrderById(@PathVariable("code") Long id) {
        try {
            OrderModel order = orderService.readOrderById(id);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{code}")
    public ResponseEntity<OrderModel> updateOrder(@PathVariable("code") Long id, @Valid @RequestBody OrderModel order) {
        try {
            OrderModel updatedOrder = orderService.updateOrder(id, order);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("code") Long id) {
        try {
            orderService.deleteOrderById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
