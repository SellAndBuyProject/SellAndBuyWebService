package com.sanvalero.SellAndBuy.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
@Entity(name = "product")
public class Product {

    @Schema(description = "Product identifier", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Product name", example = "Levi's shorts", required = true)
    @NotBlank
    @Column
    private String name;

    @Schema(description = "Product description", example = "High-rise denim shorts from Levi's", required = true)
    @NotBlank
    @Column
    private String description;

    @Schema(description = "URL with a image of that product", example = "www.sellandbuy.com/foto.jpg")
    @Lob
    @Column
    private String image;

    @Schema(description = "Product price", example = "15.75", required = true)
    @NotBlank
    @Column
    @Min(value = 0)
    private float price;

    @Schema(description = "Product category", example = "Women's", required = true)
    @NotBlank
    @Column
    private String category;

    @Schema(description = "Product size", example = "36", required = true)
    @NotBlank
    @Column
    private int size;

    @Schema(description = "Indicates if the product is new", example = "true", required = true)
    @NotBlank
    @Column
    private boolean isNew;

    @Schema(description = "Date the product is uploaded", example = "2022-01-01")
    @Column(name = "register_date")
    private LocalDate registerDate;

    @Schema(description = "User who sells the product")
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User userSeller;

    @Schema(description = "Users who have the product in their wishlist and are therefore potential users (they will probably buy it)")
    @ManyToMany(mappedBy = "wishlist", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<User> potentialUsers;

    @Schema(description = "User who has visited this product")
    @ManyToMany(mappedBy = "history", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<User> visitingUsers;

    public Product() {
        potentialUsers = new ArrayList<>();
        visitingUsers = new ArrayList<>();
    }

    public void addPotentialUser (User user) {
        potentialUsers.add(user);
    }

    public void addVisitingUsers (User user) {
        visitingUsers.add(user);
    }

}
