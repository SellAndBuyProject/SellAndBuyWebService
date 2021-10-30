package com.sanvalero.SellAndBuy.controller;

import com.sanvalero.SellAndBuy.domain.Pedido;
import com.sanvalero.SellAndBuy.domain.Producto;
import com.sanvalero.SellAndBuy.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Pedido", description = "Controller de Pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Devuelve la lista de pedidos de un usuario identificado por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido correcto.", content = @Content(schema = @Schema(implementation = Producto.class)))
    })
    @GetMapping(value = "/pedidos/{idUsuario}", produces = "application/json")
    public ResponseEntity<List<Pedido>> getPedidosByUSerId(@PathVariable int idUsuario) {
        List<Pedido> pedidos = pedidoService.findAllByUser(idUsuario);
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

}
