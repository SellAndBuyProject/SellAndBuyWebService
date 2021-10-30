package com.sanvalero.SellAndBuy.controller;

import com.sanvalero.SellAndBuy.domain.Producto;
import com.sanvalero.SellAndBuy.exception.ProductoNotFoundException;
import com.sanvalero.SellAndBuy.response.Response;
import com.sanvalero.SellAndBuy.service.ProductoService;
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

import java.util.Set;

import static com.sanvalero.SellAndBuy.response.Response.NOT_FOUND;

@RestController
@Tag(name = "Producto", description = "Controller de Productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Devuelve la lista completa de los productos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos.", content = @Content(schema = @Schema(implementation = Producto.class)))
    })
    @GetMapping(value = "/productos", produces = "application/json")
    public ResponseEntity<Set<Producto>> getProductos() {
        Set<Producto> productos = productoService.findAll();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @Operation(summary = "Devuelve un producto identificado por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto correcto.", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "El producto no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/productos/{id}", produces = "application/json")
    public ResponseEntity<Producto> getProducto(@PathVariable int id) {
        Producto producto = productoService.findById(id);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo producto.")
    @ApiResponses(value =
    @ApiResponse(responseCode = "201", description = "Producto registrado con éxito.", content = @Content(schema = @Schema(implementation = Producto.class))))
    @PostMapping(value = "/productos", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Producto> registrarProducto(@RequestBody Producto nuevoProducto) {
        Producto producto = productoService.addProducto(nuevoProducto);
        return new ResponseEntity<>(producto, HttpStatus.CREATED);
    }

    @Operation(summary = "Modifica un producto registrado, identificado por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto modificado con éxito", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "El producto no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/productos/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Producto> modificarProducto(@PathVariable int id, @RequestBody Producto detalleProducto) {
        Producto producto = productoService.updateProducto(id, detalleProducto);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un producto identificado por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado con éxito", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "El producto no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/productos/{id}", produces = "application/json")
    public ResponseEntity<Response> eliminaProducto(@PathVariable int id) {
        productoService.deleteProducto(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(ProductoNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(ProductoNotFoundException pnfe) {
        Response response = Response.errorResponse(NOT_FOUND, pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
