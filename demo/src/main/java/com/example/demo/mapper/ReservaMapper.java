package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.Entities.Reserva;
import com.example.demo.dto.ReservaDTO;

@Mapper(componentModel = "spring")
public interface ReservaMapper {
    
    @Mapping(target = "clienteId", source = "cliente.id")
    @Mapping(target = "mesaId", source = "mesa.id")
    @Mapping(target = "nomeCliente", source = "cliente.nome")
    @Mapping(target = "numeroMesa", source = "mesa.numero")
    @Mapping(target = "capacidadeMesa", source = "mesa.capacidade")
    ReservaDTO toDTO(Reserva reserva);

    @Mapping(target = "cliente.id", source = "clienteId")
    @Mapping(target = "mesa.id", source = "mesaId")
    @Mapping(target = "cliente.nome", ignore = true)
    @Mapping(target = "cliente.email", ignore = true)
    @Mapping(target = "cliente.telefone", ignore = true)
    @Mapping(target = "mesa.numero", ignore = true)
    @Mapping(target = "mesa.capacidade", ignore = true)
    @Mapping(target = "mesa.status", ignore = true)
    @Mapping(target = "horaReserva", ignore = true)
    Reserva toEntity(ReservaDTO reservaDTO);

    
    List<ReservaDTO> toDTOList(List<Reserva> reservas);
}
