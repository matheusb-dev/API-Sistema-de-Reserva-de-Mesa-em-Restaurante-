package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByReservaId(Long reservaId);

    List<Pedido> findByItemId(Long itemId);

    @Query("SELECT p FROM Pedido p WHERE p.reserva.cliente.id = :clienteId")
    List<Pedido> findByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT p FROM Pedido p JOIN p.reserva r WHERE r.id = :reservaId")
    List<Pedido> findPedidosByReserva(@Param("reservaId") Long reservaId);
}