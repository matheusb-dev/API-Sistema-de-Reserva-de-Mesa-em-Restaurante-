package com.example.demo.service;

import com.example.demo.dto.ProdutoDTO;
import com.example.demo.Entities.Produto;
import com.example.demo.mapper.ProdutoMapper;
import com.example.demo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

    public List<ProdutoDTO> findAll() {
        return produtoRepository.findAll().stream()
                .map(produtoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDTO findById(Long id) {
        return produtoRepository.findById(id)
                .map(produtoMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
    }

    @Transactional
    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        validarProduto(produtoDTO);
        Produto produto = produtoMapper.toEntity(produtoDTO);
        return produtoMapper.toDTO(produtoRepository.save(produto));
    }

    @Transactional
    public ProdutoDTO update(ProdutoDTO produtoDTO) {
        validarProduto(produtoDTO);
        Produto produto = produtoRepository.findById(produtoDTO.getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        
        produtoMapper.updateEntityFromDTO(produtoDTO, produto);
        return produtoMapper.toDTO(produtoRepository.save(produto));
    }

    @Transactional
    public void delete(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produtoRepository.delete(produto);
    }

    private void validarProduto(ProdutoDTO produtoDTO) {
        if (produtoDTO.getNome() == null || produtoDTO.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome do produto é obrigatório");
        }
        if (produtoDTO.getPreco() == null || produtoDTO.getPreco() <= 0) {
            throw new RuntimeException("Preço do produto deve ser maior que zero");
        }
    }
}