package com.sanvalero.SellAndBuy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "pedido")
public class Pedido {

    @Schema(description = "Identificador del pedido", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private int id;

    @Schema(description = "Precio total del pedido", example = "58.95")
    @Column
    private float total;

    @Schema(description = "Fecha en la que se realiza el pedido", example = "2021-04-01")
    @Column
    private LocalDate fechaCompra;

    @Schema(description = "Detalles del pedido", example = "1 Pantalón vaquero 12.75")
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<DetallePedido> lineas;

    @Schema(description = "Indica el identificador del usuario que sube la prenda", example = "1")
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

}
