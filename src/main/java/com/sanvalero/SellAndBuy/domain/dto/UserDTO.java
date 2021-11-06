package com.sanvalero.SellAndBuy.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@Data
@NoArgsConstructor
public class UserDTO {

    private String name;
    private String email;
    private String password;
    private String favStyle;
    private String favColor;
    private String size;
}
