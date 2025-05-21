package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Mesa;

@Repository
public interface Imesarepository extends JpaRepository<Mesa, Long> {

    Optional<Mesa> findById(Long id);

    List<Mesa> findByNumero(Integer numero);

}
