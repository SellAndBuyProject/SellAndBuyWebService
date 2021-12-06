package com.sanvalero.SellAndBuy.controller;

import com.sanvalero.SellAndBuy.domain.Product;
import com.sanvalero.SellAndBuy.domain.User;
import com.sanvalero.SellAndBuy.domain.dto.UserDTO;
import com.sanvalero.SellAndBuy.exception.*;
import com.sanvalero.SellAndBuy.response.Response;
import com.sanvalero.SellAndBuy.service.UserService;
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

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.RestController;

import static com.sanvalero.SellAndBuy.response.Response.*;

@RestController
@Tag(name = "Users", description = "User management")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

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

    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/users/{id}", produces = "application/json")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        logger.info("Start getUserById");
        User user = userService.findById(id);
        logger.info("End getUserById");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Search user by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = User.class)))
    })
    @GetMapping(value = "/users/name", produces = "application/json")
    public ResponseEntity<User> getUserByName(@RequestParam(name = "name", defaultValue = "") String name) {
        logger.info("Start getUserByName");
        User user = userService.findByName(name);
        logger.info("End getUserByName");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Get the object of the user who has logged in through email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<User> loginUser(@RequestBody UserDTO userDTO) {
        logger.info("Start loginUser");
        User user = userService.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());
        logger.info("End loginUser");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Get the user's products history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The history has been obtained correctly", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/users/{id}/history", produces = "application/json")
    public ResponseEntity<List<Product>> getUserHistory(@PathVariable long id) {
        logger.info("Start getUserHistory");
        List<Product> history = userService.findHistory(id);
        logger.info("End getUserHistory");
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "200", description = "There is already a user with this name or email", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Required fields have not been completed", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "/users", produces = "application/json", consumes = "application/json")
    public ResponseEntity<User> addUser(@RequestBody UserDTO userDTO) {
        logger.info("Start addUser");
        User user = userService.addUser(userDTO);
        logger.info("End addUser");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Operation(summary = "Add a product to the wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successfully added to the wishlist", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "The product does not exist", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "200", description = "The product is already added to the wishlist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "/users/{userId}/wishlist/product/{productId}", produces = "application/json")
    public ResponseEntity<User> addProductToWishlist(@PathVariable long userId, @PathVariable long productId) {
        logger.info("Start addProductToWishlist");
        User user = userService.addProductToWishlist(userId, productId);
        logger.info("End addProductToWishlist");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Operation(summary = "Add a product to the history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The product has been successfully registered in the history", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "The product does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "/users/{userId}/history/product/{productId}", produces = "application/json")
    public ResponseEntity<User> addProductToHistory(@PathVariable long userId, @PathVariable long productId) {
        logger.info("Start addProductToHistory");
        User user = userService.addProductToHistory(userId, productId);
        logger.info("End addProductToHistory");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Operation(summary = "Make a username change identified by your id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "username changed", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "200", description = "There is already a user with this name", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PatchMapping(value = "/users/{id}/change-name", produces = "application/json")
    public ResponseEntity<User> changeName(@PathVariable long id, @RequestBody UserDTO userDTO) {
        logger.info("Start changeName");
        User user = userService.changeName(id, userDTO.getName());
        logger.info("End changeName");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Make a password change to a user identified by his id and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "401", description = "The identification is incorrect", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PatchMapping(value = "/users/{id}/change-password", produces = "application/json")
    public ResponseEntity<User> changePassword(@PathVariable long id, @RequestBody UserDTO userDTO) {
        logger.info("Start changePassword");
        User user = userService.changePass(id, userDTO.getPassword(), userDTO.getNewPassword());
        logger.info("End changePassword");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Delete an user identified by his id and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "401", description = "The identification is incorrect", content = @Content(schema = @Schema(implementation = Response.class)))
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
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "The product does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/users/{userId}/wishlist/product/{productId}", produces = "application/json")
    public ResponseEntity<Response> deleteProductFromWishlist(@PathVariable long userId, @PathVariable long productId) {
        logger.info("Start deleteProductFromWishlist");
        userService.deleteProductFromWishlist(userId, productId);
        logger.info("End deleteProductFromWishlist");
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
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

    @ExceptionHandler(UserDuplicateException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> handleUserDuplicateException(UserDuplicateException ude) {
        Response response = Response.errorResponse(USER_DUPLICATE, ude.getMessage());
        logger.error(ude.getMessage(), ude);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(ProductDuplicateException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> handleProductDuplicateException(ProductDuplicateException pde) {
        Response response = Response.errorResponse(PRODUCT_DUPLICATE, pde.getMessage());
        logger.error(pde.getMessage(), pde);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(UserMissingDataException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleUserMissingDataException(UserMissingDataException umde) {
        Response response = Response.errorResponse(BAD_REQUEST, umde.getMessage());
        logger.error(umde.getMessage(), umde);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Response> handleUnauthorizedException(UnauthorizedException ue) {
        Response response = Response.errorResponse(UNAUTHORIZED, ue.getMessage());
        logger.error(ue.getMessage(), ue);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
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
