package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Entities.Reserva_Entidade;

public interface ReservaRepository extends JpaRepository<Reserva_Entidade, Long> {

    List<Reserva_Entidade> findByStatus(String status);

    List<Reserva_Entidade> findByStatusContainingIgnoreCase(String status);

    List<Reserva_Entidade> findByClienteId(Long clienteId);

    List<Reserva_Entidade> findByMesaId(Long mesaId);

    List<Reserva_Entidade> findByDataReservaAfter(LocalDateTime data);

    List<Reserva_Entidade> findByDataReservaBetween(LocalDateTime inicio, LocalDateTime fim);
}
