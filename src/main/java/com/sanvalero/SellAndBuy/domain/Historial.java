package com.sanvalero.SellAndBuy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @NotBlank
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private int id;

    @Schema(description = "Indica el identificador del usuario que ha visto el producto", example = "1", required = true)
    @NotBlank
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToMany(mappedBy = "historialList")
    private List<Producto> productos;
}
