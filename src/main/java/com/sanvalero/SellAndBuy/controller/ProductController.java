package com.sanvalero.SellAndBuy.controller;

import com.sanvalero.SellAndBuy.domain.Product;
import com.sanvalero.SellAndBuy.exception.ProductNotFoundException;
import com.sanvalero.SellAndBuy.response.Response;
import com.sanvalero.SellAndBuy.service.ProductService;
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

import java.util.List;
import java.util.Set;

import static com.sanvalero.SellAndBuy.response.Response.NOT_FOUND;

@RestController
@Tag(name = "Product", description = "Controller de Productos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product's list", content = @Content(schema = @Schema(implementation = Product.class)))
    })
    @GetMapping(value = "/products", produces = "application/json")
    public ResponseEntity<Set<Product>> getProducts() {
        Set<Product> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Get product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "The product does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/products/{id}", produces = "application/json")
    public ResponseEntity<Product> getProduct(@PathVariable long id) {
        Product product = productService.findById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Operation(summary = "Search products by category")
    @ApiResponses(value =
            @ApiResponse(responseCode = "200", description = "Product's list", content = @Content(schema = @Schema(implementation = Product.class)))
    )
    @GetMapping(value = "/products/category", produces = "application/json")
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam(name = "category", defaultValue = "") String category) {
        List<Product> products = productService.findByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Search products by name")
    @ApiResponses(value =
    @ApiResponse(responseCode = "200", description = "Product's list", content = @Content(schema = @Schema(implementation = Product.class))))
    @GetMapping(value = "/products/name", produces = "application/json")
    public ResponseEntity<List<Product>> getProductsByName(@RequestParam(name = "name", defaultValue = "") String name) {
        List<Product> products = productService.findByName(name);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Add a new product")
    @ApiResponses(value =
    @ApiResponse(responseCode = "201", description = "Product was added", content = @Content(schema = @Schema(implementation = Product.class))))
    @PostMapping(value = "/products", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Product> addProduct(@RequestBody Product newProduct) {
        Product product = productService.addProduct(newProduct);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @Operation(summary = "Modify a registered product, identified by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product was modified", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "The product does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/products/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Product> modifyProduct(@PathVariable long id, @RequestBody Product productDetail) {
        Product product = productService.updateProduct(id, productDetail);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Operation(summary = "Delete a product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product was deleted", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "The product does not exist", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/products/{id}", produces = "application/json")
    public ResponseEntity<Response> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(ProductNotFoundException pnfe) {
        Response response = Response.errorResponse(NOT_FOUND, pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
