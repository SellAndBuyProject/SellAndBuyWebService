package com.sanvalero.SellAndBuy.domain;

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
@Entity(name = "usuario")
public class Usuario {

    @Schema(description = "Identificador del usuario", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int id;

    @Schema(description = "Nombre de usuario", example = "Usuario", required = true)
    @NotBlank
    @Column
    private String nombre;

    @Schema(description = "Email del usuario", example = "user@gmail.com", required = true)
    @NotBlank
    @Column
    private String email;

    @Schema(description = "Password del usuario", example = "1234qwer", required = true)
    @NotBlank
    @Column
    private String password;

    @Schema(description = "Estilo de ropa favorito", example = "Casual")
    @Column(name = "estilo_favorito")
    private String estiloFavorito;

    @Schema(description = "Color de ropa favorito", example = "Casual")
    @Column(name = "color_favorito")
    private String colorFavorito;

    @Schema(description = "Talla de ropa habitual", example = "M")
    @Column
    private String talla;

    @Schema(description = "Pedidos del usuario en cuestión")
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private List<Pedido> pedidos;

    @Schema(description = "Indica el identificador del usuario que sube el producto", example = "1", required = true)
    @ManyToOne
    @JoinColumn(name = "id_historial")
    private Historial historial;

    @Schema(description = "Indica el identificador del usuario que sube el producto", example = "1", required = true)
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Wishlist wishlist;
}
