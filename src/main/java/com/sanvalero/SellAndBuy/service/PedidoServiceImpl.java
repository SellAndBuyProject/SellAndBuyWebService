package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Pedido;
import com.sanvalero.SellAndBuy.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService{

    @Autowired
    private PedidoRepository pedidoRepository;

    /**
     * Servicio que devuelve una lista de objetos de tipo Pedido de un
     * usuario identificado por su id.
     * @param idUsuario id del usuario del que se quieren obtener los pedidos.
     * @return lista de objetos de tipo Pedido.
     */
    @Override
    public List<Pedido> findAllByUser(int idUsuario) {
        Set<Pedido> pedidos = pedidoRepository.findAll();

        return pedidos.stream()
                .filter(pedido -> pedido.getUsuario().getId() == idUsuario)
                .collect(Collectors.toList());
    }

}
