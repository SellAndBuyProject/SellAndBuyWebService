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
public class UserDTO {

    @Schema(description = "Username", example = "User")
    private String name;

    @Schema(description = "Email", example = "user@gmail.com")
    private String email;

    @Schema(description = "Old password", example = "1234qwer")
    private String password;

    @Schema(description = "New password", example = "12345qwer")
    private String newPassword;

    @Schema(description = "Favorite style", example = "Casual")
    private String favStyle;

    @Schema(description = "Favorite color", example = "Casual")
    private String favColor;

    @Schema(description = "Usual size", example = "M")
    private String size;
}
