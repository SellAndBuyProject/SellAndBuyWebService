package com.sanvalero.SellAndBuy.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@Data
@Builder
@AllArgsConstructor
@Entity(name = "user")
public class User {

    @Schema(description = "User identifier", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Schema(description = "Username", example = "User", required = true)
    @NotBlank
    @Column
    private String name;

    @Schema(description = "Email", example = "user@gmail.com", required = true)
    @NotBlank
    @Column
    private String email;

    @Schema(description = "Password", example = "1234qwer", required = true)
    @NotBlank
    @Column
    private String password;

    @Schema(description = "Favorite style", example = "Casual")
    @Column(name = "fav_style")
    private String favStyle;

    @Schema(description = "Favorite color", example = "Casual")
    @Column(name = "fav_color")
    private String favColor;

    @Schema(description = "Usual size", example = "M")
    @Column
    private String size;

    @Schema(description = "User orders")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Order> orders;

    @Schema(description = "Products for sale")
    @OneToMany(mappedBy = "userSeller", cascade = CascadeType.REMOVE)
    private List<Product> products;

    @Schema(description = "Wishlist of this user")
    @ManyToMany
    @JoinTable(name = "wishlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> wishlist;

    @Schema(description = "History of this user")
    @ManyToMany
    @JoinTable(name = "history",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> history;

    public User() {
        orders = new ArrayList<>();
        products = new ArrayList<>();
        wishlist = new ArrayList<>();
        history = new ArrayList<>();
    }

    public void addOrder (Order order) {
        orders.add(order);
    }

    public void addProduct (Product product) {
        products.add(product);
    }

    public void addProductToWishlist (Product product) {
        wishlist.add(product);
    }

    public void addProductToHistory (Product product) {
        history.add(product);
    }

}
