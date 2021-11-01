package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Pedido;
import com.sanvalero.SellAndBuy.exception.PedidoNotFoundException;
import com.sanvalero.SellAndBuy.exception.PedidoNotSuccess;
import com.sanvalero.SellAndBuy.exception.UsuarioNotFoundException;
import com.sanvalero.SellAndBuy.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    /**
     * Servicio que devuelve un objeto de tipo Pedido identificado por su id.
     * @param idPedido el id del pedido que se quiere consultar.
     * @return objeto de tipo Pedido.
     */
    @Override
    public Pedido findById(int idPedido) {
        return pedidoRepository.findById(idPedido).
                orElseThrow(() -> new PedidoNotFoundException(idPedido));
    }

    /**
     * Servicio que registra un pedido nuevo.
     * @param pedido objeto de tipo Pedido que se quiere registrar.
     * @return el objeto de tipo Pedido que se ha registrado.
     */
    @Override
    public Pedido addPedido(Pedido pedido) {
        // Se comprueba que el usuario relacionado con el pedido existe
        if (!pedidoRepository.existsById(pedido.getUsuario().getId()))
            throw new UsuarioNotFoundException();

        // Se comprueba que el pedido tenga al menos un detalle
        if (pedido.getLineas().isEmpty())
            throw new PedidoNotSuccess();

        // Se calcula el precio de los productos que hay en el pedido
        final float[] precio = {0};
        pedido.getLineas().forEach(detallePedido -> precio[0] += detallePedido.getSubtotal());

        // Se asigna el precio total al pedido
        pedido.setTotal(precio[0]);

        // Se asigna la fecha del pedido
        pedido.setFechaCompra(LocalDate.now());

        return pedidoRepository.save(pedido);
    }

}
