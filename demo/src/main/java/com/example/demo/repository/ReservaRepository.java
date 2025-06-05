package com.example.demo.repository;

import com.example.demo.Entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    
    @Query("SELECT r FROM Reserva r WHERE r.mesa.id = :mesaId AND r.horario = :horario")
    List<Reserva> findReservasByMesaAndHorario(
        @Param("mesaId") Long mesaId, 
        @Param("horario") LocalDateTime horario
    );

    // Opcional: Método para buscar todas as reservas de uma mesa em um determinado dia
    @Query("SELECT r FROM Reserva r WHERE r.mesa.id = :mesaId AND DATE(r.horario) = DATE(:data)")
    List<Reserva> findReservasByMesaAndData(
        @Param("mesaId") Long mesaId, 
        @Param("data") LocalDateTime data
    );
}