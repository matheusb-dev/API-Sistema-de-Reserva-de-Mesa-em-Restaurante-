package com.example.demo.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import com.example.demo.Entities.ItemDeCardapio;

public interface ItemCardapioRepository extends JpaRepository< ItemDeCardapio, Long> {
    
    Optional<ItemDeCardapio> findByNome(String nome);

    List<ItemDeCardapio> findByNomeContainingIgnoreCase(String nome);

    List<ItemDeCardapio> findByPrecoLessThan(BigDecimal preco);

    List<ItemDeCardapio> findByPrecoBetween(BigDecimal min, BigDecimal max);
}