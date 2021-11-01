package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Pedido;
import com.sanvalero.SellAndBuy.domain.Usuario;

import java.util.List;

public interface PedidoService {

    List<Pedido> findAllByUser(int idUsuario); // Devuelve los pedidos del usuario que corresponda con el id
    Pedido findById(int idPedido); // Devuelve un pedido identificado por su id
    Pedido addPedido(Pedido pedido); // Añade un pedido

}
