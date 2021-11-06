package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Order;
import com.sanvalero.SellAndBuy.domain.User;
import com.sanvalero.SellAndBuy.exception.OrderNotFoundException;
import com.sanvalero.SellAndBuy.exception.OrderNotSuccess;
import com.sanvalero.SellAndBuy.exception.UserNotFoundException;
import com.sanvalero.SellAndBuy.repository.OrderRepository;
import com.sanvalero.SellAndBuy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Service that allow to get a list of orders of a certain user
     * @param userId user identifier from whom you want to obtain the orders
     * @return List of objects of type Order
     */
    @Override
    public List<Order> findAllByUser(long userId) {
        Set<Order> orders = orderRepository.findAll();

        return orders.stream()
                .filter(order -> order.getUser().getId() == userId)
                .collect(Collectors.toList());
    }

    /**
     * Service that allows to get a order by id
     * @param orderId order identifier
     * @return Order object
     */
    @Override
    public Order findById(long orderId) {
        return orderRepository.findById(orderId).
                orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    /**
     * Service that allows to user place an order
     * @param order Order object
     * @param userId user identified
     * @return Order object added
     */
    @Override
    public Order addOrder(Order order, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        order.setUser(user);

        // Check that the order has at least one detail
        if (order.getDetails().isEmpty())
            throw new OrderNotSuccess();

        // Calculate the price of products
        final float[] precio = {0};
        order.getDetails().forEach(detallePedido -> precio[0] += detallePedido.getPrice());

        order.setTotalPrice(precio[0]); // Total price
        order.setDate(LocalDate.now()); // Sate

        return orderRepository.save(order);
    }

}
