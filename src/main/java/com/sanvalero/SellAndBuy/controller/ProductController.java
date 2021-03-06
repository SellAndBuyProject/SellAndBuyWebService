package com.sanvalero.SellAndBuy.controller;

import com.sanvalero.SellAndBuy.domain.Product;
import com.sanvalero.SellAndBuy.domain.dto.ProductDTO;
import com.sanvalero.SellAndBuy.exception.NotImplementedException;
import com.sanvalero.SellAndBuy.exception.ProductNotFoundException;
import com.sanvalero.SellAndBuy.exception.UserNotFoundException;
import com.sanvalero.SellAndBuy.response.Response;
import com.sanvalero.SellAndBuy.service.ProductService;
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
import java.util.Set;

import static com.sanvalero.SellAndBuy.response.Response.*;

@RestController
@Tag(name = "Product", description = "Product management")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Operation(summary = "Get all products") // Description of the operation
    @ApiResponses(value = { // Possible answers and the type of content
            @ApiResponse(responseCode = "200", description = "Product's list", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class))))
    })
    @GetMapping(value = "/products", produces = "application/json")
    public ResponseEntity<Set<Product>> getProducts() {
        logger.info("Start getProducts");
        Set<Product> products = productService.findAll();
        logger.info("End getProducts");
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Get product by id") // Description of the operation
    @ApiResponses(value = { // Possible answers and the type of content
            @ApiResponse(responseCode = "200", description = "Product found", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "The product does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/products/{id}", produces = "application/json")
    public ResponseEntity<Product> getProduct(@PathVariable long id) {
        logger.info("Start getProduct");
        Product product = productService.findById(id);
        logger.info("End getProduct");
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Operation(summary = "Get a user's product list") // Description of the operation
    @ApiResponses(value = { // Possible answers and the type of content
            @ApiResponse(responseCode = "200", description = "The list of products has been obtained correctly", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class)))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/users/{id}/products", produces = "application/json")
    public ResponseEntity<List<Product>> getProductsByUser(@PathVariable long id) {
        logger.info("Start getProductsByUser");
        List<Product> products = productService.findAllByUser(id);
        logger.info("End getProductsByUser");
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Search products by category") // Description of the operation
    @ApiResponses(value = { // Possible answers and the type of content
            @ApiResponse(responseCode = "200", description = "Product's list", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class))))
    })
    @GetMapping(value = "/products/category", produces = "application/json")
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam(name = "category", defaultValue = "") String category) {
        logger.info("Start getProductsByCategory");
        List<Product> products = productService.findByCategory(category);
        logger.info("End getProductsByCategory");
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Search products by name") // Description of the operation
    @ApiResponses(value = { // Possible answers and the type of content
            @ApiResponse(responseCode = "200", description = "Product's list", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class)))),
            @ApiResponse(responseCode = "200", description = "It has been searched by a name composed of more than two words", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/products/name", produces = "application/json")
    public ResponseEntity<List<Product>> getProductsByName(@RequestParam(name = "name", defaultValue = "") String name) {
        logger.info("Start getProductsByName");
        List<Product> products = productService.findByName(name);
        logger.info("End getProductsByName");
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Add a new product") // Description of the operation
    @ApiResponses(value = { // Possible answers and the type of content
            @ApiResponse(responseCode = "201", description = "Product was added", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "/users/{id}/products", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Product> addProduct(@PathVariable long id, @RequestBody ProductDTO productDTO) {
        logger.info("Start addProduct");
        Product product = productService.addProduct(id, productDTO);
        logger.info("End addProduct");
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @Operation(summary = "Modify a registered product, identified by id") // Description of the operation
    @ApiResponses(value = { // Possible answers and the type of content
            @ApiResponse(responseCode = "200", description = "Product was modified", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "The product does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/products/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Product> modifyProduct(@PathVariable long id, @RequestBody ProductDTO productDTO) {
        logger.info("Start modifyProduct");
        Product product = productService.updateProduct(id, productDTO);
        logger.info("End modifyProduct");
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Operation(summary = "Delete a product by id") // Description of the operation
    @ApiResponses(value = { // Possible answers and the type of content
            @ApiResponse(responseCode = "200", description = "Product was deleted", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "The product does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/products/{id}", produces = "application/json")
    public ResponseEntity<Response> deleteProduct(@PathVariable long id) {
        logger.info("Start deleteProduct");
        productService.deleteProduct(id);
        logger.info("End deleteProduct");
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

    @ExceptionHandler(NotImplementedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response> handleNotImplementedException(NotImplementedException nie) {
        Response response = Response.errorResponse(NOT_IMPLEMENTED, nie.getMessage());
        logger.error(nie.getMessage(), nie);
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