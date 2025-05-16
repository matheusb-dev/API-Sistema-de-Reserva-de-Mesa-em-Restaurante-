package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.Entities.Cliente;
import com.example.demo.dto.Clientedto;


@Mapper(componentModel = "spring")
public interface ClienteMapper {

    //@Mapping(target = "id", ignore = true)
    Cliente toEntity(Clientedto dto);

    Clientedto toDto(Cliente usuarios);

    List<Clientedto> tDtos(List<Cliente> usuarios);
}
