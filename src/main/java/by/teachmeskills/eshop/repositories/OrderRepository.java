package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> getOrdersByUserId(int id);

    Order getOrderById(int id);
}
