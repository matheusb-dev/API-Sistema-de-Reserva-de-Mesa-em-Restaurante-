package com.example.demo.mapper;

import com.example.demo.Entities.Cliente;
import com.example.demo.dto.ClienteDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-05T19:16:25-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public ClienteDTO toDTO(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        ClienteDTO clienteDTO = new ClienteDTO();

        clienteDTO.setCpf( cliente.getCpf() );
        clienteDTO.setEmail( cliente.getEmail() );
        clienteDTO.setId( cliente.getId() );
        clienteDTO.setNome( cliente.getNome() );
        clienteDTO.setTelefone( cliente.getTelefone() );

        return clienteDTO;
    }

    @Override
    public Cliente toEntity(ClienteDTO clienteDTO) {
        if ( clienteDTO == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setCpf( clienteDTO.getCpf() );
        cliente.setEmail( clienteDTO.getEmail() );
        cliente.setId( clienteDTO.getId() );
        cliente.setNome( clienteDTO.getNome() );
        cliente.setTelefone( clienteDTO.getTelefone() );

        return cliente;
    }

    @Override
    public void updateEntityFromDTO(ClienteDTO clienteDTO, Cliente cliente) {
        if ( clienteDTO == null ) {
            return;
        }

        cliente.setCpf( clienteDTO.getCpf() );
        cliente.setEmail( clienteDTO.getEmail() );
        cliente.setNome( clienteDTO.getNome() );
        cliente.setTelefone( clienteDTO.getTelefone() );
    }
}
