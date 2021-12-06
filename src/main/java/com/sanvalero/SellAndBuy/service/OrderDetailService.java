package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.OrderDetail;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
public interface OrderDetailService {

    OrderDetail addProductToCart(long userId, long productId);
    void deleteProductFromCart(long orderId, long productId);
}
