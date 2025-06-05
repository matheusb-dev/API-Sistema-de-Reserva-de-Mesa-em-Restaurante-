package com.example.demo.mapper;

import com.example.demo.dto.ProdutoDTO;
import com.example.demo.Entities.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    ProdutoDTO toDTO(Produto produto);

    Produto toEntity(ProdutoDTO produtoDTO);

    void updateEntityFromDTO(ProdutoDTO produtoDTO, @MappingTarget Produto produto);
}