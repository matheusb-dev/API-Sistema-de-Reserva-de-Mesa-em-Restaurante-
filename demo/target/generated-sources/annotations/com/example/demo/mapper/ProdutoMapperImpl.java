package com.example.demo.mapper;

import com.example.demo.Entities.Produto;
import com.example.demo.dto.ProdutoDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-05T19:16:25-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ProdutoMapperImpl implements ProdutoMapper {

    @Override
    public ProdutoDTO toDTO(Produto produto) {
        if ( produto == null ) {
            return null;
        }

        ProdutoDTO produtoDTO = new ProdutoDTO();

        produtoDTO.setDescricao( produto.getDescricao() );
        produtoDTO.setId( produto.getId() );
        produtoDTO.setNome( produto.getNome() );
        produtoDTO.setPreco( produto.getPreco() );

        return produtoDTO;
    }

    @Override
    public Produto toEntity(ProdutoDTO produtoDTO) {
        if ( produtoDTO == null ) {
            return null;
        }

        Produto produto = new Produto();

        produto.setDescricao( produtoDTO.getDescricao() );
        produto.setId( produtoDTO.getId() );
        produto.setNome( produtoDTO.getNome() );
        produto.setPreco( produtoDTO.getPreco() );

        return produto;
    }

    @Override
    public void updateEntityFromDTO(ProdutoDTO produtoDTO, Produto produto) {
        if ( produtoDTO == null ) {
            return;
        }

        produto.setDescricao( produtoDTO.getDescricao() );
        produto.setId( produtoDTO.getId() );
        produto.setNome( produtoDTO.getNome() );
        produto.setPreco( produtoDTO.getPreco() );
    }
}
