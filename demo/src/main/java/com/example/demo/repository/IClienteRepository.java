package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Cliente;



@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long>{
      Optional<Cliente> findByNome(String nome);
      List<Cliente> findByid(long id);

}