package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAllByUser(long userId);
    Order findById(long orderId);
    Order addOrder(Order order, long userId);

}
