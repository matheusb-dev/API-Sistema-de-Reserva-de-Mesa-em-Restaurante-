package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Mesa;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

    Optional<Mesa> findByNumero(Integer numero);

    List<Mesa> findByStatus(String status);

    List<Mesa> findByStatusIgnoreCase(String status);

    List<Mesa> findByCapacidadeGreaterThanEqual(Integer capacidade);

    boolean existsByNumero(Integer numero);
}