package com.example.demo.service;

import com.example.demo.dto.PedidoDTO;
import com.example.demo.Entities.Cliente;
import com.example.demo.Entities.Mesa;
import com.example.demo.Entities.Pedido;
import com.example.demo.Entities.Produto;
import com.example.demo.mapper.PedidoMapper;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    public List<PedidoDTO> findAll() {
        return pedidoRepository.findAll().stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PedidoDTO findById(Long id) {
        return pedidoRepository.findById(id)
                .map(pedidoMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    @Transactional
    public PedidoDTO save(PedidoDTO pedidoDTO) {
        // Validar cliente
        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Validar mesa
        Mesa mesa = mesaRepository.findById(pedidoDTO.getMesaId())
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));

        // Validar produtos e calcular valor total
        List<Produto> produtos = pedidoDTO.getProdutosIds().stream()
                .map(produtoId -> produtoRepository.findById(produtoId)
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + produtoId)))
                .collect(Collectors.toList());

        Double valorTotal = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();

        // Criar pedido
        Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
        pedido.setCliente(cliente);
        pedido.setMesa(mesa);
        pedido.setProdutos(produtos);
        pedido.setValorTotal(valorTotal);
        pedido.setDataPedido(LocalDateTime.now());

        return pedidoMapper.toDTO(pedidoRepository.save(pedido));
    }

    @Transactional
    public void delete(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedidoRepository.delete(pedido);
    }

    @Transactional
    public PedidoDTO adicionarProdutos(Long pedidoId, List<Long> novoProdutosIds) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        List<Produto> novosProdutos = novoProdutosIds.stream()
                .map(produtoId -> produtoRepository.findById(produtoId)
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + produtoId)))
                .collect(Collectors.toList());

        pedido.getProdutos().addAll(novosProdutos);
        
        // Recalcular valor total
        Double valorTotal = pedido.getProdutos().stream()
                .mapToDouble(Produto::getPreco)
                .sum();
        
        pedido.setValorTotal(valorTotal);

        return pedidoMapper.toDTO(pedidoRepository.save(pedido));
    }
}