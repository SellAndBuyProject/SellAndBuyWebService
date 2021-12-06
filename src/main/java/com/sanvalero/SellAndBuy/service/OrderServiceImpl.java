package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Order;
import com.sanvalero.SellAndBuy.domain.OrderDetail;
import com.sanvalero.SellAndBuy.domain.Product;
import com.sanvalero.SellAndBuy.exception.OrderAlreadyPlacedException;
import com.sanvalero.SellAndBuy.exception.OrderNotFoundException;
import com.sanvalero.SellAndBuy.exception.OrderNotSuccess;
import com.sanvalero.SellAndBuy.exception.UserNotFoundException;
import com.sanvalero.SellAndBuy.repository.OrderRepository;
import com.sanvalero.SellAndBuy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Service that allows to get a order by id
     * @param orderId order identifier
     * @return Order object
     */
    @Override
    public Order findById(long orderId) {
        return orderRepository.findById(orderId).
                orElseThrow(OrderNotFoundException::new);
    }

    /**
     * Service that allow to get a list of orders of a certain user
     * @param userId user identifier from whom you want to obtain the orders
     * @return List of objects of type Order
     */
    @Override
    public List<Order> findAllByUser(long userId) {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Set<Order> orders = orderRepository.findAll();

        return orders.stream()
                .filter(order -> order.getUser().getId() == userId)
                .collect(Collectors.toList());
    }

    /**
     * Service that allows to user place an order
     * @param orderId order identified
     * @return Order object that has been placed
     */
    @Override
    public Order placeOrder(long orderId) {
        Order order = orderRepository.findById(orderId).
                orElseThrow(OrderNotFoundException::new);

        if(order.isPlaced()) {
            throw new OrderAlreadyPlacedException(orderId);
        }

        order.setDate(LocalDate.now());
        order = orderRepository.save(order);

        for(OrderDetail detail : order.getDetails()) { // Get order details
            Product product = detail.getProduct(); // Product of each detail line
            product.setSold(true);
            detail.setPrice(product.getPrice());

            List<OrderDetail> details = new ArrayList<>(); // Details of the order
            details.add(detail); // Add each detail line to details list
            order.setDetails(details); // Set details to order
        }

        // Check that the order has at least one detail
        if (order.getDetails().isEmpty())
            throw new OrderNotSuccess();

        // Calculate the price of products
        float[] price = {0};
        order.getDetails().forEach(orderDetail -> price[0] += orderDetail.getPrice());
        order.setTotalPrice(price[0]); // Total price
        order.setPlaced(true); // The order has been placed
        order = orderRepository.save(order);

        return order;
    }

}
