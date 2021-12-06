package com.sanvalero.SellAndBuy.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DATA TRANSFER OBJECT
 * @version Curso 2020-2021
 * @author: veronica
 */
@Data
@NoArgsConstructor
public class UserDTO {

    @Schema(description = "Username", example = "User")
    private String name;

    @Schema(description = "Email", example = "user@example.com")
    private String email;

    @Schema(description = "Old password", example = "1234")
    private String password;

    @Schema(description = "New password", example = "12345")
    private String newPassword;

    @Schema(description = "Favorite style", example = "Casual")
    private String favStyle;

    @Schema(description = "Favorite color", example = "Casual")
    private String favColor;

    @Schema(description = "Usual size", example = "M")
    private String size;
}
