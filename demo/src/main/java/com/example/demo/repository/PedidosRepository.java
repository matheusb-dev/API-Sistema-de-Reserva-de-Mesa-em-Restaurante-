package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Cliente;

@Repository
public interface PedidosRepository extends JpaRepository<Cliente, Long> {
}