package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.repositories.OrderRepository;
import by.teachmeskills.eshop.services.OrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order create(Order entity) {
        return orderRepository.save(entity);
    }

    @Override
    public List<Order> read() {
        return orderRepository.findAll();
    }

    @Override
    public Order update(Order entity) {
        Optional<Order> order = orderRepository.findById(entity.getId());
        if (order.isPresent()) {
            return orderRepository.save(entity);
        }
        log.error("Order doesn't exist");
        return null;
    }

    @Override
    public void delete(int id) {
        orderRepository.deleteById(id);
    }
}
