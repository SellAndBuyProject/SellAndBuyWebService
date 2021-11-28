package com.sanvalero.SellAndBuy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@Data
@Builder
@AllArgsConstructor
@Entity(name = "order")
public class Order {

    @Schema(description = "Order identifier", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Total order price", example = "58.95")
    @Column
    private float totalPrice;

    @Schema(description = "Indicates if the order has been placed", example = "true")
    @Column(name = "is_placed")
    private boolean isPlaced;

    @Schema(description = "Date the order is placed", example = "2022-01-01")
    @Column
    private LocalDate date;

    @Schema(description = "Order details", example = "1 Denim shorts 12.75")
    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<OrderDetail> details;

    @Schema(description = "Identifier of the user who uploads the garment")
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public Order() {
        details = new ArrayList<>();
    }

    public void addDetail(OrderDetail detail) {
        details.add(detail);
    }
}
