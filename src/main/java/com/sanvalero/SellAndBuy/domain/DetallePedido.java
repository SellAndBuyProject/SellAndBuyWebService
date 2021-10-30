package com.sanvalero.SellAndBuy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "detalle_pedido")
public class DetallePedido {

    @Schema(description = "Identificador del detalle", example = "1", required = true)
    @NotBlank
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_pedido")
    private int id;

    @Schema(description = "Detalle de la línea", example = "Pantalón vaquero", required = true)
    @NotBlank
    @Column
    private String detalle;

    @Schema(description = "Precio de los artículos de la línea", example = "12.75", required = true)
    @NotBlank
    @Column
    private float subtotal;

    @Schema(description = "Identificador del pedido al que se añade la línea", example = "1")
    @ManyToOne
    @JoinColumn(name = "id_pedido")
    @JsonBackReference
    private Pedido pedido;

    @Schema(description = "Identificador del producto de la línea", example = "1", required = true)
    @NotBlank
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;
}
