package com.sanvalero.SellAndBuy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "historial")
public class Historial {

    @Schema(description = "Identificador del historial", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private int id;

    @Schema(description = "Indica el identificador del usuario que ha visto el producto", example = "1")
    @OneToOne
    @JoinColumn(name = "id_usuario", updatable = false, nullable = false)
    private Usuario usuario;

    @Schema(description = "Productos del historial", example = "Pantalón vaquero")
    @JsonBackReference
    private List<Producto> productos;
}
