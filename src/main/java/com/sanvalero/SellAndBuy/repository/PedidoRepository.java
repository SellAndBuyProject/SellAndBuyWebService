package com.sanvalero.SellAndBuy.repository;

import com.sanvalero.SellAndBuy.domain.Pedido;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, Integer> {

    Set<Pedido> findAll();

}
