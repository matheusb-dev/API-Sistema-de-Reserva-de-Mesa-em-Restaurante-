package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.Entities.usuario;
import com.example.demo.dto.UsuarioDto;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    //@Mapping(target = "id", ignore = true)
    usuario toEntity(UsuarioDto dto);

    UsuarioDto toDto(usuario usuarios);

    List<UsuarioDto> tDtos(List<usuario> usuarios);
}
