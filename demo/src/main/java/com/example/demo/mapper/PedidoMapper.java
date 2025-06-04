package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.Entities.Pedido;
import com.example.demo.dto.PedidoDTO;

@Mapper(componentModel = "spring")
public interface PedidoMapper {
    
    @Mapping(target = "reservaId", source = "reserva.id")
    @Mapping(target = "itemId", source = "item.id")
    @Mapping(target = "nomeItem", source = "item.nome")
    @Mapping(target = "precoUnitario", source = "item.preco")
    @Mapping(target = "clienteId", source = "reserva.cliente.id")
    @Mapping(target = "nomeCliente", source = "reserva.cliente.nome")
    PedidoDTO toDTO(Pedido pedido);

    @Mapping(target = "reserva.id", source = "reservaId")
    @Mapping(target = "item.id", source = "itemId")
    @Mapping(target = "reserva.cliente", ignore = true)
    @Mapping(target = "reserva.mesa", ignore = true)
    @Mapping(target = "reserva.dataReserva", ignore = true)
    @Mapping(target = "reserva.horaReserva", ignore = true)
    @Mapping(target = "reserva.status", ignore = true)
    @Mapping(target = "item.nome", ignore = true)
    @Mapping(target = "item.descricao", ignore = true)
    @Mapping(target = "item.preco", ignore = true)
    Pedido toEntity(PedidoDTO pedidoDTO);

    List<PedidoDTO> toDTOList(List<Pedido> pedidos);
}