package com.sanvalero.SellAndBuy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */

@Data
@Builder
@AllArgsConstructor
@Entity(name = "producto")
public class Producto {

    @Schema(description = "Identificador del producto", example = "1", required = true)
    @NotBlank
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private int id;

    @Schema(description = "Nombre del producto", example = "Vaqueros azules", required = true)
    @NotBlank
    @Column
    private String nombre;

    @Schema(description = "Descripción del producto", example = "Vaqueros largos de la marca Lee, azul gastado", required = true)
    @NotBlank
    @Column
    private String descripcion;

    @Schema(description = "URL de la imagen del producto", example = "www.sellandbuy.com/foto.jpg", required = true)
    @NotBlank
    @Column
    private String imagen;

    @Schema(description = "Precio del producto", example = "15.75", required = true)
    @NotBlank
    @Column
    private float precio;

    @Schema(description = "Categoría del producto", example = "Pantalones", required = true)
    @NotBlank
    @Column
    private String categoria;

    @Schema(description = "Talla del producto", example = "40", required = true)
    @NotBlank
    @Column
    private int talla;

    @Schema(description = "Indica si el producto es nuevo", example = "true", required = true)
    @NotBlank
    @Column
    private boolean nuevo;

    @Schema(description = "Fecha en la que se sube el producto", example = "2021-04-01", required = true)
    @NotBlank
    @Column
    private LocalDate fechaSubida;

    @Schema(description = "Indica el identificador del usuario que sube el producto", example = "1", required = true)
    @NotBlank
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Schema(description = "Wishlists en las que se encuentra el producto", example = "Wishlist del usuario con id=1", required = true)
    @ManyToMany(mappedBy = "productos")
    @JsonBackReference (value="get-wishlist")
    private List<Wishlist> wishlists;
}
