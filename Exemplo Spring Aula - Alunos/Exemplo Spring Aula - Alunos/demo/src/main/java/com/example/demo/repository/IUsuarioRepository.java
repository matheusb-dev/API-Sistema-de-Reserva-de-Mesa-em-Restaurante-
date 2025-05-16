package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<usuario, Long>{
    
}