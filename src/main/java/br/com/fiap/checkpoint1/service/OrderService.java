package br.com.fiap.checkpoint1.service;

import br.com.fiap.checkpoint1.model.OrderModel;
import br.com.fiap.checkpoint1.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderModel createOrder(OrderModel order) {
        return orderRepository.save(order);
    }

    public List<OrderModel> readAllOrders() {
        return orderRepository.findAll();
    }

    public OrderModel readOrderById(Long id) {
        Optional<OrderModel> order = findById(id);
        if (order.isEmpty()) {
            throw new EntityNotFoundException("Pedido não encontrado com ID: " + id);
        }
        return order.get();
    }

    public OrderModel updateOrder(Long id, OrderModel order) {
        Optional<OrderModel> existingOrder = findById(id);
        if (existingOrder.isEmpty()) {
            throw new EntityNotFoundException("Pedido não encontrado com ID: " + id);
        }
        existingOrder.get().setClientName(order.getClientName());
        existingOrder.get().setTotalValue(order.getTotalValue());
        return orderRepository.save(existingOrder.get());
    }

    public void deleteOrderById(Long id) {
        try {
            orderRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Pedido não encontrado com ID: " + id);
        }
    }

    private Optional<OrderModel> findById(Long id) {
        return orderRepository.findById(id);
    }
}
