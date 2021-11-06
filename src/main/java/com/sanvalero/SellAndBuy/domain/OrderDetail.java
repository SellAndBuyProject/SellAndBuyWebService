package com.sanvalero.SellAndBuy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "detalle_pedido")
public class OrderDetail {

    @Schema(description = "Detail identifier", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Line detail", example = "Denim shorts")
    @Column
    private int detail;

    @Schema(description = "Price of the items on the line", example = "12.75")
    @Column
    private float price;

    @Schema(description = "Order identifier to which the line is added", example = "1")
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    @Schema(description = "Product identifier", example = "1")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
