package com.sanvalero.SellAndBuy.controller;

import com.sanvalero.SellAndBuy.domain.Order;
import com.sanvalero.SellAndBuy.domain.Product;
import com.sanvalero.SellAndBuy.domain.User;
import com.sanvalero.SellAndBuy.domain.dto.UserDTO;
import com.sanvalero.SellAndBuy.exception.UnauthorizedException;
import com.sanvalero.SellAndBuy.exception.UserDuplicateException;
import com.sanvalero.SellAndBuy.exception.UserMissingDataException;
import com.sanvalero.SellAndBuy.exception.UserNotFoundException;
import com.sanvalero.SellAndBuy.response.Response;
import com.sanvalero.SellAndBuy.service.OrderService;
import com.sanvalero.SellAndBuy.service.UserService;
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

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.RestController;

import static com.sanvalero.SellAndBuy.response.Response.*;
import static org.ietf.jgss.GSSException.UNAUTHORIZED;

@RestController
@Tag(name = "Usuarios", description = "Gestión de los usuarios")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User's list", content = @Content(schema = @Schema(implementation = User.class)))
    })
    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<Set<User>> getUsers() {
        logger.info("Start getUsers");
        Set<User> users = userService.findAll();
        logger.info("End getUsers");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Get user by name and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/login", produces = "application/json")
    public ResponseEntity<User> getLoginUser(@RequestBody UserDTO userDTO) {
        logger.info("Start getLoginUser");
        User user = userService.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());
        logger.info("End getLoginUser");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Get the user's products history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The history has been obtained correctly", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/users/{id}/history", produces = "application/json")
    public ResponseEntity<List<Product>> getUserHistory(@PathVariable long userId) {
        logger.info("Start getUserHistory");
        List<Product> history = userService.findHistory(userId);
        logger.info("End getUserHistory");
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @Operation(summary = "Get orders by user")
    @ApiResponses(value =
    @ApiResponse(responseCode = "200", description = "Order found", content = @Content(schema = @Schema(implementation = Order.class)))
    )
    @GetMapping(value = "/user/{id}/order", produces = "application/json")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable long userId) {
        List<Order> orders = orderService.findAllByUser(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "406", description = "There is already a user with this email", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Required fields have not been completed", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "/users", produces = "application/json", consumes = "application/json")
    public ResponseEntity<User> addUser(@RequestBody User newUser) {
        logger.info("Start addUser");
        User user = userService.addUser(newUser);
        logger.info("fin addUser");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Operation(summary = "Add a product to the wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successfully added to the wishlist", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "/users/{id}/wishlist/product/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<User> addProductToWishlist(@PathVariable long userId, @PathVariable long productId) {
        logger.info("Start addProductToWishlist");
        User user = userService.addProductToWishlist(userId, productId);
        logger.info("End addProductToWishlist");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Operation(summary = "Add a product to the history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The product has been successfully registered in the history", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "/users/{id}/history/product/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Product> addProductToHistory(@PathVariable long userId, @PathVariable long productId) {
        logger.info("Start addProductToHistory");
        Product product = userService.addProductToHistory(userId, productId);
        logger.info("End addProductToHistory");
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @Operation(summary = "Make a username change identified by your id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "username changed", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PatchMapping(value = "/users/{id}/change-name", produces = "application/json")
    public ResponseEntity<User> changeName(@PathVariable long id, @RequestParam(value = "name", defaultValue = "") String newName) {
        logger.info("Start changeName");
        User user = userService.changeName(id, newName);
        logger.info("End changeName");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Make a password change to a user identified by his id and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "403", description = "The identification is incorrect", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PatchMapping(value = "/users/{id}/change-password", produces = "application/json")
    public ResponseEntity<User> changePassword(@PathVariable long id, @RequestBody UserDTO oldUserDTO, @RequestBody UserDTO newUserDTO) {
        logger.info("Start changePassword");
        User user = userService.changePass(id, oldUserDTO.getPassword(), newUserDTO.getPassword());
        logger.info("End changePassword");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Delete an user identified by his id and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "403", description = "The identification is incorrect", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/users/{id}", produces = "application/json")
    public ResponseEntity<Response> deleteUser(@PathVariable long id, @RequestBody UserDTO userDTO) {
        logger.info("Start deleteUser");
        userService.deleteUser(id, userDTO.getPassword());
        logger.info("End deleteUser");
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @Operation(summary = "Remove a product from the user's wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted from wishlist", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/users/{id}/wishlist/product/{id}", produces = "application/json")
    public ResponseEntity<Response> deleteProductFromWishlist(@PathVariable long idUser, @RequestBody long idProduct) {
        logger.info("Start deleteProductFromWishlist");
        userService.deleteProductFromWishlist(idUser, idProduct);
        logger.info("End deleteProductFromWishlist");
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(UserNotFoundException unfe) {
        Response response = Response.errorResponse(NOT_FOUND, unfe.getMessage());
        logger.error(unfe.getMessage(), unfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserDuplicateException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<Response> handleException(UserDuplicateException ude) {
        Response response = Response.errorResponse(NOT_ACCEPTABLE, ude.getMessage());
        logger.error(ude.getMessage(), ude);
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UserMissingDataException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleException(UserMissingDataException umde) {
        Response response = Response.errorResponse(BAD_REQUEST, umde.getMessage());
        logger.error(umde.getMessage(), umde);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Response> handleException(UnauthorizedException ue) {
        Response response = Response.errorResponse(UNAUTHORIZED, ue.getMessage());
        logger.error(ue.getMessage(), ue);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}
