package com.sanvalero.SellAndBuy.controller;

import com.sanvalero.SellAndBuy.domain.Order;
import com.sanvalero.SellAndBuy.exception.OrderAlreadyPlacedException;
import com.sanvalero.SellAndBuy.exception.OrderNotFoundException;
import com.sanvalero.SellAndBuy.exception.OrderNotSuccess;
import com.sanvalero.SellAndBuy.exception.UserNotFoundException;
import com.sanvalero.SellAndBuy.response.Response;
import com.sanvalero.SellAndBuy.service.OrderService;
import com.sanvalero.SellAndBuy.util.ConstantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

import java.util.List;

import static com.sanvalero.SellAndBuy.response.Response.*;

@RestController
@Tag(name = "Order", description = "Order management")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Get orders by id") // Description of the operation
    @ApiResponses(value = { // Possible answers and the type of content
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

    @Operation(summary = "Get orders by user") // Description of the operation
    @ApiResponses(value = { // Possible answers and the type of content
            @ApiResponse(responseCode = "200", description = "Order found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Order.class)))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/users/{id}/order", produces = "application/json")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable long id) {
        logger.info("Start getOrdersByUser");
        List<Order> orders = orderService.findAllByUser(id);
        logger.info("Start getOrdersByUser");
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Operation(summary = "Add a new order") // Description of the operation
    @ApiResponses(value = { // Possible answers and the type of content
            @ApiResponse(responseCode = "201", description = "Processed order", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "200", description = "The order has already placed", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "The order does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "/orders/{id}", produces = "application/json")
    public ResponseEntity<Order> placeOrder(@PathVariable long id) {
        logger.info("Start placeOrder");
        Order order = orderService.placeOrder(id);
        logger.info("End placeOrder");
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleOrderNotFoundException (OrderNotFoundException onfe) {
        Response response = Response.errorResponse(NOT_FOUND, onfe.getMessage());
        logger.error(onfe.getMessage(), onfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleUserNotFoundException(UserNotFoundException unfe) {
        Response response = Response.errorResponse(NOT_FOUND, unfe.getMessage());
        logger.error(unfe.getMessage(), unfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderAlreadyPlacedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> handleOrderAlreadyPlacedException (OrderAlreadyPlacedException oaple) {
        Response response = Response.errorResponse(ALREADY_PLACED, oaple.getMessage());
        logger.error(oaple.getMessage(), oaple);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(OrderNotSuccess.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> handleOrderNotSuccessException (OrderNotSuccess ons) {
        Response response = Response.errorResponse(ORDER_NOT_SUCCESS, ons.getMessage());
        logger.error(ons.getMessage(), ons);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response> handleException(Exception exception) {
        Response response = Response.errorResponse(INTERNAL_SERVER_ERROR, ConstantUtil.INTERNAL_SERVER_ERROR + exception.getMessage());
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}