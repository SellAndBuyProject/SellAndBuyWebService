package com.sanvalero.SellAndBuy.repository;

import com.sanvalero.SellAndBuy.domain.Wishlist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@Repository
public interface WishlistRepository extends CrudRepository<Wishlist, Integer> {

    Wishlist findByUsuarioId(int id);
}
