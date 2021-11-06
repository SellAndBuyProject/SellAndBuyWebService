package com.sanvalero.SellAndBuy.controller;

import com.sanvalero.SellAndBuy.domain.Order;
import com.sanvalero.SellAndBuy.response.Response;
import com.sanvalero.SellAndBuy.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Order", description = "Controller de Pedidos")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Get orders by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "The order does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/orders/{id}", produces = "application/json")
    public ResponseEntity<Order> getOrderById(@PathVariable long id) {
        Order order = orderService.findById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Operation(summary = "Add a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order registered", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "/orders/user/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Order> addOrder(@RequestBody Order newOrder, @PathVariable long userId) {
        Order order = orderService.addOrder(newOrder, userId);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}