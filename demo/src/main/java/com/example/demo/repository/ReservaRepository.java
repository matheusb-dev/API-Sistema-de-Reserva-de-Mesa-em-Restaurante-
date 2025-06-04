package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Reserva;


public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByStatus(String status);

    List<Reserva> findByStatusContainingIgnoreCase(String status);

    List<Reserva> findByClienteId(Long clienteId);

    List<Reserva> findByMesaId(Long mesaId);

    List<Reserva> findByDataReservaAfter(LocalDateTime data);

    List<Reserva> findByDataReservaBetween(LocalDateTime inicio, LocalDateTime fim);
    
}
