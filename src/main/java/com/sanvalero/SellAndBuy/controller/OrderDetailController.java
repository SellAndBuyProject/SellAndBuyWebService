package com.sanvalero.SellAndBuy.controller;

import com.sanvalero.SellAndBuy.domain.Order;
import com.sanvalero.SellAndBuy.domain.OrderDetail;
import com.sanvalero.SellAndBuy.exception.*;
import com.sanvalero.SellAndBuy.response.Response;
import com.sanvalero.SellAndBuy.service.OrderDetailService;
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

import static com.sanvalero.SellAndBuy.response.Response.*;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@RestController
@Tag(name = "OrderDetail", description = "Order details (cart) management")
public class OrderDetailController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderDetailService orderDetailService;

    @Operation(summary = "Add a new order") // Description of the operation
    @ApiResponses(value = { // Possible answers and the type of content
            @ApiResponse(responseCode = "201", description = "Product registered", content = @Content(schema = @Schema(implementation = OrderDetail.class))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "The product does not exist", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "200", description = "The product has been sold", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "/users/{userId}/cart/products/{productId}", produces = "application/json")
    public ResponseEntity<OrderDetail> addProductToCart(@PathVariable long userId, @PathVariable long productId) {
        logger.info("Start addProductToCart");
        OrderDetail orderDetail = orderDetailService.addProductToCart(userId, productId);
        logger.info("End addProductToCart");
        return new ResponseEntity<>(orderDetail, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a product by id") // Description of the operation
    @ApiResponses(value = { // Possible answers and the type of content
            @ApiResponse(responseCode = "200", description = "Product was deleted", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "200", description = "The order has already placed", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "The order does not exist", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "The product does not exist", content = @Content(schema = @Schema(implementation = Response.class))),
    })
    @DeleteMapping(value = "/orders/{orderId}/products/{productId}", produces = "application/json")
    public ResponseEntity<Response> deleteProductFromCart(@PathVariable long orderId, @PathVariable long productId) {
        logger.info("Start deleteProductToCart");
        orderDetailService.deleteProductFromCart(orderId, productId);
        logger.info("End deleteProductToCart");
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
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

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleProductNotFoundException(ProductNotFoundException pnfe) {
        Response response = Response.errorResponse(NOT_FOUND, pnfe.getMessage());
        logger.error(pnfe.getMessage(), pnfe);
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

    @ExceptionHandler(ProductSoldException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> handleProductSoldException (ProductSoldException onfe) {
        Response response = Response.errorResponse(PRODUCT_SOLD, onfe.getMessage());
        logger.error(onfe.getMessage(), onfe);
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
