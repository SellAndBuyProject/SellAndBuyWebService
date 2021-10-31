package com.sanvalero.SellAndBuy.controller;

import com.sanvalero.SellAndBuy.domain.Producto;
import com.sanvalero.SellAndBuy.domain.Usuario;
import com.sanvalero.SellAndBuy.domain.Wishlist;
import com.sanvalero.SellAndBuy.response.Response;
import com.sanvalero.SellAndBuy.service.WishlistService;
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

/**
 * @version Curso 2020-2021
 * @author: veronica
 */

@RestController
@Tag(name = "Wishlists", description = "Gestión de las wishlist")
public class WishlistController {

    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private WishlistService wishlistService;

    @Operation(summary = "Devuelve la wishlist del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Wishlist encontrada", content = @Content(schema = @Schema(implementation = Wishlist.class)))
    })
    @GetMapping(value = "usuarios/{id}/wishlist")
    public ResponseEntity<Wishlist> wishlistByUsuario(@PathVariable int id) {
        logger.info("inicio wishlistByUsuario");
        Wishlist wishlist = wishlistService.findbyUsuario(id);
        logger.info("fin wishlistByUsuario");
        return new ResponseEntity<>(wishlist, HttpStatus.OK);
    }

    @Operation(summary = "Guarda un producto en la wishlist y la devuelve actualizada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto añadido", content = @Content(schema = @Schema(implementation = Wishlist.class)))
    })
    @PostMapping(value = "/wishlist/{id}/productos", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Wishlist> addProducto(@RequestBody Usuario usuario, @RequestBody Producto producto) {
        logger.info("inicio addProducto");
        Wishlist wishlist = wishlistService.addProducto(usuario, producto);
        logger.info("fin addProducto");
        return new ResponseEntity<>(wishlist, HttpStatus.CREATED);
    }

    @Operation(summary = "Elimina un producto de la wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El producto ha sido eliminado", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/usuarios/{id}/producto/{id}")
    public ResponseEntity<Response> deleteProductoFromWishlist(@PathVariable int idUsuario, @PathVariable int idProducto) {
        logger.info("inicio deleteProductoFromWishlist");
            wishlistService.deleteProducto(idUsuario, idProducto);
        logger.info("fin deleteProductoFromWishlist");
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

}
