package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Order;

import java.util.List;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
public interface OrderService {

    List<Order> findAllByUser(long userId);
    Order findById(long orderId);
    Order placeOrder(long orderId);
}
