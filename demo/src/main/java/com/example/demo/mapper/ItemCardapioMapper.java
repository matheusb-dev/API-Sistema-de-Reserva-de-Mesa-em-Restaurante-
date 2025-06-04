package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.Entities.ItemDeCardapio;
import com.example.demo.dto.ItemCardapioDTO;

@Mapper(componentModel = "spring")
public interface ItemCardapioMapper {
    
    ItemCardapioDTO toDTO(ItemDeCardapio item);

    ItemDeCardapio toEntity(ItemCardapioDTO itemDTO);

    List<ItemCardapioDTO> toDTOList(List<ItemDeCardapio> itens);
}