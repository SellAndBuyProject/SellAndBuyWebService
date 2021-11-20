package com.sanvalero.SellAndBuy.controller;

import com.sanvalero.SellAndBuy.domain.Order;
import com.sanvalero.SellAndBuy.response.Response;
import com.sanvalero.SellAndBuy.service.OrderService;
import com.sanvalero.SellAndBuy.util.ConstantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Order", description = "Order management")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Get orders by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "The order does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/orders/{id}", produces = "application/json")
    public ResponseEntity<Order> getOrderById(@PathVariable long id) {
        logger.info("Start getOrderById");
        Order order = orderService.findById(id);
        logger.info("End getOrderById");
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Operation(summary = "Add a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order registered", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "/orders/user/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Order> addOrder(@RequestBody Order newOrder, @PathVariable long userId) {
        logger.info("Start addOrder");
        Order order = orderService.addOrder(newOrder, userId);
        logger.info("End addOrder");
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response> handleException(Exception exception) {
        Response response = Response.errorResponse(500, ConstantUtil.INTERNAL_SERVER_ERROR + exception.getMessage());
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}