package com.example.demo.mapper;

import com.example.demo.Entities.Usuario;
import com.example.demo.dto.UsuarioDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-18T11:22:50-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public UsuarioDTO toDTO(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setCpf( usuario.getCpf() );
        usuarioDTO.setDataNascimento( usuario.getDataNascimento() );
        usuarioDTO.setEmail( usuario.getEmail() );
        usuarioDTO.setId( usuario.getId() );
        usuarioDTO.setNome( usuario.getNome() );
        usuarioDTO.setSenha( usuario.getSenha() );
        usuarioDTO.setTelefone( usuario.getTelefone() );

        return usuarioDTO;
    }

    @Override
    public Usuario toEntity(UsuarioDTO usuarioDTO) {
        if ( usuarioDTO == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setCpf( usuarioDTO.getCpf() );
        usuario.setDataNascimento( usuarioDTO.getDataNascimento() );
        usuario.setEmail( usuarioDTO.getEmail() );
        usuario.setId( usuarioDTO.getId() );
        usuario.setNome( usuarioDTO.getNome() );
        usuario.setSenha( usuarioDTO.getSenha() );
        usuario.setTelefone( usuarioDTO.getTelefone() );

        return usuario;
    }

    @Override
    public List<UsuarioDTO> toDTOList(List<Usuario> usuarios) {
        if ( usuarios == null ) {
            return null;
        }

        List<UsuarioDTO> list = new ArrayList<UsuarioDTO>( usuarios.size() );
        for ( Usuario usuario : usuarios ) {
            list.add( toDTO( usuario ) );
        }

        return list;
    }
}
