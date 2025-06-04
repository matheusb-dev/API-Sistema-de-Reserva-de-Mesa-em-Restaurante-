package com.example.demo.service;

import com.example.demo.Entities.Mesa;
import com.example.demo.dto.MesaDTO;
import com.example.demo.mapper.MesaMapper;
import com.example.demo.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private MesaMapper mesaMapper;

    public List<MesaDTO> listarTodas() {
        return mesaMapper.toDTOList(mesaRepository.findAll());
    }

    public List<MesaDTO> listarDisponiveis() {
        return mesaMapper.toDTOList(mesaRepository.findByStatus("Livre"));
    }

    public Optional<MesaDTO> buscarPorId(Long id) {
        return mesaRepository.findById(id).map(mesaMapper::toDTO);
    }

    public MesaDTO salvar(MesaDTO mesaDTO) {
        // Verificar se já existe mesa com o mesmo número
        if (mesaDTO.getId() == null && mesaRepository.existsByNumero(mesaDTO.getNumero())) {
            throw new IllegalArgumentException("Já existe uma mesa com o número " + mesaDTO.getNumero());
        }

        // Definir status padrão se não fornecido
        if (mesaDTO.getStatus() == null || mesaDTO.getStatus().isEmpty()) {
            mesaDTO.setStatus("Livre");
        }

        Mesa mesa = mesaMapper.toEntity(mesaDTO);
        return mesaMapper.toDTO(mesaRepository.save(mesa));
    }

    public MesaDTO atualizarStatus(Long id, String novoStatus) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mesa não encontrada"));
        
        if (!novoStatus.matches("^(Livre|Ocupada|Reservada)$")) {
            throw new IllegalArgumentException("Status inválido. Use: Livre, Ocupada ou Reservada");
        }

        mesa.setStatus(novoStatus);
        return mesaMapper.toDTO(mesaRepository.save(mesa));
    }

    public void deletar(Long id) {
        if (!mesaRepository.existsById(id)) {
            throw new IllegalArgumentException("Mesa não encontrada");
        }
        mesaRepository.deleteById(id);
    }

    public List<MesaDTO> buscarPorCapacidade(Integer capacidadeMinima) {
        return mesaMapper.toDTOList(mesaRepository.findByCapacidadeGreaterThanEqual(capacidadeMinima));
    }
}