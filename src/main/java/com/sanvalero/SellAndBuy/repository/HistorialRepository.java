package com.sanvalero.SellAndBuy.repository;

import com.sanvalero.SellAndBuy.domain.Historial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@Repository
public interface HistorialRepository extends CrudRepository<Historial, Integer>  {


}
