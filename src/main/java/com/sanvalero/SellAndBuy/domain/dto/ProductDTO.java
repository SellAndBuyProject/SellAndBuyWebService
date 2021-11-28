package com.sanvalero.SellAndBuy.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@Data
@NoArgsConstructor
public class ProductDTO {

    @Schema(description = "Product identifier", example = "1")
    private long idProduct;

    @Schema(description = "Product name", example = "Levi's shorts")
    private String name;

    @Schema(description = "Product description", example = "High-rise denim shorts from Levi's")
    private String description;

    @Schema(description = "URL with a image of that product", example = "www.sellandbuy.com/foto.jpg")
    private String image;

    @Schema(description = "Product price", example = "15.75")
    private float price;

    @Schema(description = "Product category", example = "Women's")
    private String category;

    @Schema(description = "Product size", example = "36")
    private int size;

    @Schema(description = "Indicates if the product is new", example = "true")
    private boolean isNew;
}
