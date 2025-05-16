package com.example.demo.mapper;

import com.example.demo.Entities.Cliente;
import com.example.demo.dto.Clientedto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-16T20:02:48-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public Cliente toEntity(Clientedto dto) {
        if ( dto == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setEmail( dto.getEmail() );
        cliente.setId( dto.getId() );
        cliente.setNome( dto.getNome() );
        cliente.setTelefone( dto.getTelefone() );

        return cliente;
    }

    @Override
    public Clientedto toDto(Cliente usuarios) {
        if ( usuarios == null ) {
            return null;
        }

        String email = null;
        String nome = null;
        String telefone = null;

        email = usuarios.getEmail();
        nome = usuarios.getNome();
        telefone = usuarios.getTelefone();

        Long id = null;
        String cpf = null;
        String senha = null;
        LocalDate dataNascimento = null;

        Clientedto clientedto = new Clientedto( id, nome, cpf, email, senha, telefone, dataNascimento );

        clientedto.setId( usuarios.getId() );

        return clientedto;
    }

    @Override
    public List<Clientedto> tDtos(List<Cliente> usuarios) {
        if ( usuarios == null ) {
            return null;
        }

        List<Clientedto> list = new ArrayList<Clientedto>( usuarios.size() );
        for ( Cliente cliente : usuarios ) {
            list.add( toDto( cliente ) );
        }

        return list;
    }
}
