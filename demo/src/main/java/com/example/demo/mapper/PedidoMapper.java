package com.example.demo.mapper;

import com.example.demo.dto.PedidoDTO;
import com.example.demo.Entities.Pedido;
import com.example.demo.Entities.Produto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public PedidoDTO toDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setClienteId(pedido.getCliente() != null ? pedido.getCliente().getId() : null);
        dto.setMesaId(pedido.getMesa() != null ? pedido.getMesa().getId() : null);
        dto.setValorTotal(pedido.getValorTotal());
        dto.setDataPedido(pedido.getDataPedido());
        
        if (pedido.getProdutos() != null) {
            dto.setProdutosIds(
                pedido.getProdutos().stream()
                    .map(Produto::getId)
                    .collect(Collectors.toList())
            );
        }
        
        return dto;
    }

    public Pedido toEntity(PedidoDTO dto) {
        if (dto == null) {
            return null;
        }

        return Pedido.builder()
                .id(dto.getId())
                .valorTotal(dto.getValorTotal())
                .dataPedido(dto.getDataPedido())
                .build();
    }
}