package com.example.demo.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.ItemDeCardapio;

@Repository
public interface ItemCardapioRepository extends JpaRepository<ItemDeCardapio, Long> {
    
    Optional<ItemDeCardapio> findByNome(String nome);

    List<ItemDeCardapio> findByNomeContainingIgnoreCase(String nome);

    List<ItemDeCardapio> findByPrecoLessThan(BigDecimal preco);

    List<ItemDeCardapio> findByPrecoBetween(BigDecimal min, BigDecimal max);
}