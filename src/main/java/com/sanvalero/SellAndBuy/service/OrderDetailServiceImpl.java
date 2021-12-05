package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Order;
import com.sanvalero.SellAndBuy.domain.OrderDetail;
import com.sanvalero.SellAndBuy.domain.Product;
import com.sanvalero.SellAndBuy.domain.User;
import com.sanvalero.SellAndBuy.exception.*;
import com.sanvalero.SellAndBuy.repository.OrderDetailRepository;
import com.sanvalero.SellAndBuy.repository.OrderRepository;
import com.sanvalero.SellAndBuy.repository.ProductRepository;
import com.sanvalero.SellAndBuy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Service that allows to user add a product to cart
     * @param userId user identifier
     * @param productId product identifier
     * @return OrderDetail object
     */
    @Override
    public OrderDetail addProductToCart(long userId, long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Product product = productRepository.findById(productId).
                orElseThrow(ProductNotFoundException::new);

        if(product.isSold()) { // If the product has been sold
            throw new ProductSoldException();
        }

        OrderDetail detail = new OrderDetail();
        detail.setPrice(product.getPrice());
        detail.setProduct(product);

        if(user.getOrders().size() == 0) {
            addNewOrder(user, detail);
            return detail;
        }

        List<Order> orders = new ArrayList<>(user.getOrders());

        for(Order order : orders) {
            if(!order.isPlaced()) {
                detail.setOrder(order);
                order.addDetail(detail);
                orderDetailRepository.save(detail);
                orderRepository.save(order);
                user.getOrders().addAll(orders);

            } else {
                addNewOrder(user, detail);
            }
        }

        return detail;
    }

    private void addNewOrder(User user, OrderDetail detail){
        Order newOrder = new Order();
        newOrder.setUser(user);
        orderRepository.save(newOrder);
        detail.setOrder(newOrder);
        newOrder.addDetail(detail);
        user.addOrder(newOrder);
        orderDetailRepository.save(detail);
        orderRepository.save(newOrder);
    }

    /**
     * Service that allows to user delete a product from cart
     * @param orderId order identifier
     * @param productId product identifier
     */
    @Override
    public void deleteProductFromCart(long orderId, long productId) {
        Order order = orderRepository.findById(orderId).
                orElseThrow(OrderNotFoundException::new);

        if(order.isPlaced()) {
            throw new OrderAlreadyPlacedException(orderId);
        }

        List<OrderDetail> details = order.getDetails();

        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        // List of details contained in the product
        List<OrderDetail> orderDetails = orderDetailRepository.findByProduct(product);

        // Go through the entire list to find out which one belongs to this order
        for(OrderDetail detail : orderDetails) {
            // If the detail belongs to the order from where we want to delete the product
            if(detail.getOrder() == order) {
                order.getDetails().remove(detail); // Get the detail and delete it
                details.remove(detail);
                long idDetail = detail.getId();
                orderDetailRepository.deleteById(idDetail);
            }
        }
    }

}
