package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Pedido;

import java.util.List;

public interface PedidoService {

    List<Pedido> findAllByUser(int idUsuario); // Devuelve los pedidos del usuario que corresponda con el id

}
