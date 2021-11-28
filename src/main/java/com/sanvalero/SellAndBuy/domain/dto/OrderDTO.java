package com.sanvalero.SellAndBuy.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@Data
@NoArgsConstructor
public class OrderDTO {

    @Schema(description = "Order identifier", example = "1")
    private long orderId;

    @Schema(description = "Product identifier", example = "1")
    private long productId;

    @Schema(description = "Name of the products in the cart", example = "Levi's shorts")
    private List<String> products;
}
