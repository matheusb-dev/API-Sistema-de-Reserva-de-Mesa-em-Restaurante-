package com.example.demo.mapper;

import com.example.demo.Entities.Cliente;
import com.example.demo.Entities.Mesa;
import com.example.demo.Entities.Reserva;
import com.example.demo.dto.ReservaDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-05T19:16:25-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ReservaMapperImpl implements ReservaMapper {

    @Override
    public ReservaDTO toDTO(Reserva reserva) {
        if ( reserva == null ) {
            return null;
        }

        ReservaDTO reservaDTO = new ReservaDTO();

        reservaDTO.setMesaId( reservaMesaId( reserva ) );
        reservaDTO.setClienteId( reservaClienteId( reserva ) );
        reservaDTO.setHorario( reserva.getHorario() );
        reservaDTO.setId( reserva.getId() );

        return reservaDTO;
    }

    @Override
    public Reserva toEntity(ReservaDTO reservaDTO) {
        if ( reservaDTO == null ) {
            return null;
        }

        Reserva reserva = new Reserva();

        reserva.setMesa( reservaDTOToMesa( reservaDTO ) );
        reserva.setCliente( reservaDTOToCliente( reservaDTO ) );
        reserva.setHorario( reservaDTO.getHorario() );
        reserva.setId( reservaDTO.getId() );

        return reserva;
    }

    private Long reservaMesaId(Reserva reserva) {
        if ( reserva == null ) {
            return null;
        }
        Mesa mesa = reserva.getMesa();
        if ( mesa == null ) {
            return null;
        }
        Long id = mesa.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long reservaClienteId(Reserva reserva) {
        if ( reserva == null ) {
            return null;
        }
        Cliente cliente = reserva.getCliente();
        if ( cliente == null ) {
            return null;
        }
        Long id = cliente.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Mesa reservaDTOToMesa(ReservaDTO reservaDTO) {
        if ( reservaDTO == null ) {
            return null;
        }

        Mesa mesa = new Mesa();

        mesa.setId( reservaDTO.getMesaId() );

        return mesa;
    }

    protected Cliente reservaDTOToCliente(ReservaDTO reservaDTO) {
        if ( reservaDTO == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setId( reservaDTO.getClienteId() );

        return cliente;
    }
}
