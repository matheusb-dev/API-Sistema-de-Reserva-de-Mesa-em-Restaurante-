package com.example.demo.service;

import com.example.demo.Entities.ItemDeCardapio;
import com.example.demo.Entities.Pedido;
import com.example.demo.Entities.Reserva;
import com.example.demo.dto.PedidoDTO;
import com.example.demo.mapper.PedidoMapper;
import com.example.demo.repository.ItemCardapioRepository;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ItemCardapioRepository itemRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    public List<PedidoDTO> listarTodos() {
        return pedidoMapper.toDTOList(pedidoRepository.findAll());
    }

    public Optional<PedidoDTO> buscarPorId(Long id) {
        return pedidoRepository.findById(id).map(pedidoMapper::toDTO);
    }

    public List<PedidoDTO> buscarPorReserva(Long reservaId) {
        return pedidoMapper.toDTOList(pedidoRepository.findByReservaId(reservaId));
    }

    public List<PedidoDTO> buscarPorCliente(Long clienteId) {
        return pedidoMapper.toDTOList(pedidoRepository.findByClienteId(clienteId));
    }

    public PedidoDTO criarPedido(PedidoDTO pedidoDTO) {
        // Verificar se reserva existe e está ativa
        Reserva reserva = reservaRepository.findById(pedidoDTO.getReservaId())
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada"));

        if (!"Confirmada".equals(reserva.getStatus())) {
            throw new IllegalArgumentException("Só é possível fazer pedidos para reservas confirmadas");
        }

        // Verificar se item existe
        ItemDeCardapio item = itemRepository.findById(pedidoDTO.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado"));

        // Validar quantidade
        if (pedidoDTO.getQuantidade() == null || pedidoDTO.getQuantidade() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        if (pedidoDTO.getQuantidade() > 100) {
            throw new IllegalArgumentException("Quantidade não pode ser superior a 100 unidades por pedido");
        }

        // SEMPRE calcular o valor total - cliente NÃO pode informar
        BigDecimal valorTotal = item.getPreco().multiply(BigDecimal.valueOf(pedidoDTO.getQuantidade()));
        pedidoDTO.setValorTotal(valorTotal);

        // Verificar se o valor total não é absurdo (proteção adicional)
        if (valorTotal.compareTo(new BigDecimal("99999.99")) > 0) {
            throw new IllegalArgumentException("Valor total do pedido excede o limite permitido");
        }

        Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
        pedido.setReserva(reserva);
        pedido.setItem(item);

        return pedidoMapper.toDTO(pedidoRepository.save(pedido));
    }

    public void deletar(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        // Só permite deletar se a reserva ainda estiver ativa
        if (!"Confirmada".equals(pedido.getReserva().getStatus())) {
            throw new IllegalArgumentException("Só é possível cancelar pedidos de reservas confirmadas");
        }

        pedidoRepository.deleteById(id);
    }

    public BigDecimal calcularTotalPorReserva(Long reservaId) {
        // Verificar se reserva existe
        if (!reservaRepository.existsById(reservaId)) {
            throw new IllegalArgumentException("Reserva não encontrada");
        }

        List<Pedido> pedidos = pedidoRepository.findByReservaId(reservaId);
        return pedidos.stream()
                .map(Pedido::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public PedidoDTO atualizarQuantidade(Long pedidoId, Integer novaQuantidade) {
        if (novaQuantidade <= 0 || novaQuantidade > 100) {
            throw new IllegalArgumentException("Quantidade deve estar entre 1 e 100");
        }

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        if (!"Confirmada".equals(pedido.getReserva().getStatus())) {
            throw new IllegalArgumentException("Só é possível alterar pedidos de reservas confirmadas");
        }

        pedido.setQuantidade(novaQuantidade);
        // Recalcular valor total
        BigDecimal novoValorTotal = pedido.getItem().getPreco().multiply(BigDecimal.valueOf(novaQuantidade));
        pedido.setValorTotal(novoValorTotal);

        return pedidoMapper.toDTO(pedidoRepository.save(pedido));
    }
}