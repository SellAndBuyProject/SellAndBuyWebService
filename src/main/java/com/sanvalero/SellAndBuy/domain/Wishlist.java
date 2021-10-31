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
@Entity(name = "wishlist")
public class Wishlist {

    @Schema(description = "Identificador de la wishlist", example = "1", required = true)
    @NotBlank
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_wishlist")
    private int id;

    @Schema(description = "Indica el identificador del usuario que añade el producto a su lista de deseos", example = "1", required = true)
    @NotBlank
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Schema(description = "Productos de la wishlist", example = "Pantalón vaquero")
    @ManyToMany
    @JoinTable(name = "wishlists_Producto",
            joinColumns = @JoinColumn(name = "wishlist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id", referencedColumnName = "id"))
    private List<Producto> productos;

    public void addProducto(Producto producto) {
        productos.add(producto);
    }

}
