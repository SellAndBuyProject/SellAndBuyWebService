package com.sanvalero.SellAndBuy.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@Data
@NoArgsConstructor
public class ProductDTO {

    private long idProduct;
    private String name;
    private String description;
    private float price;
    private String category;
    private int size;
    private boolean isNew;
}
