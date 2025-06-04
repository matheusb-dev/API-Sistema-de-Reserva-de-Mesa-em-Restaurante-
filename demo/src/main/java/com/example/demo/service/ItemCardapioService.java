package com.example.demo.service;

import com.example.demo.Entities.ItemDeCardapio;
import com.example.demo.dto.ItemCardapioDTO;
import com.example.demo.mapper.ItemCardapioMapper;
import com.example.demo.repository.ItemCardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ItemCardapioService {

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    @Autowired
    private ItemCardapioMapper itemCardapioMapper;

    public List<ItemCardapioDTO> listarTodos() {
        return itemCardapioMapper.toDTOList(itemCardapioRepository.findAll());
    }

    public Optional<ItemCardapioDTO> buscarPorId(Long id) {
        return itemCardapioRepository.findById(id).map(itemCardapioMapper::toDTO);
    }

    public List<ItemCardapioDTO> buscarPorNome(String nome) {
        return itemCardapioMapper.toDTOList(itemCardapioRepository.findByNomeContainingIgnoreCase(nome));
    }

    public List<ItemCardapioDTO> buscarAbaixoDoPreco(BigDecimal preco) {
        return itemCardapioMapper.toDTOList(itemCardapioRepository.findByPrecoLessThan(preco));
    }

    public List<ItemCardapioDTO> buscarPorFaixaPreco(BigDecimal precoMin, BigDecimal precoMax) {
        return itemCardapioMapper.toDTOList(itemCardapioRepository.findByPrecoBetween(precoMin, precoMax));
    }

    public ItemCardapioDTO salvar(ItemCardapioDTO itemDTO) {
        // Verificar se já existe item com o mesmo nome (apenas para novos itens)
        if (itemDTO.getId() == null) {
            Optional<ItemDeCardapio> itemExistente = itemCardapioRepository.findByNome(itemDTO.getNome());
            if (itemExistente.isPresent()) {
                throw new IllegalArgumentException("Já existe um item com o nome: " + itemDTO.getNome());
            }
        }

        // Validar preço
        if (itemDTO.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior que zero");
        }

        ItemDeCardapio item = itemCardapioMapper.toEntity(itemDTO);
        return itemCardapioMapper.toDTO(itemCardapioRepository.save(item));
    }

    public void deletar(Long id) {
        if (!itemCardapioRepository.existsById(id)) {
            throw new IllegalArgumentException("Item não encontrado");
        }
        
        // Aqui você pode adicionar uma verificação se o item tem pedidos associados
        // Por exemplo:
        // List<Pedido> pedidos = pedidoRepository.findByItemId(id);
        // if (!pedidos.isEmpty()) {
        //     throw new IllegalArgumentException("Não é possível excluir item que possui pedidos associados");
        // }
        
        itemCardapioRepository.deleteById(id);
    }

    public Optional<ItemCardapioDTO> buscarPorNomeExato(String nome) {
        return itemCardapioRepository.findByNome(nome).map(itemCardapioMapper::toDTO);
    }
}