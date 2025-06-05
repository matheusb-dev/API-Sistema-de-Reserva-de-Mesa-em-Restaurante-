package com.example.demo.service;

import com.example.demo.dto.ReservaDTO;
import com.example.demo.Entities.Cliente;
import com.example.demo.Entities.Mesa;
import com.example.demo.Entities.Reserva;
import com.example.demo.mapper.ReservaMapper;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.MesaRepository;
import com.example.demo.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ReservaMapper reservaMapper;

    public ReservaDTO findById(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada com ID: " + id));
        return reservaMapper.toDTO(reserva);
    }

    public List<ReservaDTO> findAll() {
        return reservaRepository.findAll().stream()
                .map(reservaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservaDTO save(ReservaDTO reservaDTO) {
        // Primeiro valida a disponibilidade
        validarDisponibilidade(reservaDTO);
        
        // Verificar se o cliente existe
        Cliente cliente = clienteRepository.findById(reservaDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        
        Mesa mesa = mesaRepository.findById(reservaDTO.getMesaId())
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));
        
        Reserva reserva = reservaMapper.toEntity(reservaDTO);
        reserva.setMesa(mesa);
        reserva.setCliente(cliente);
        
        // Atualizar status da mesa
        mesa.setStatus("ocupada");
        mesaRepository.save(mesa);
        
        return reservaMapper.toDTO(reservaRepository.save(reserva));
    }

    @Transactional
    public void delete(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
        
        // Liberar a mesa
        Mesa mesa = reserva.getMesa();
        mesa.setStatus("livre");
        mesaRepository.save(mesa);
        
        reservaRepository.delete(reserva);
    }

    public void validarDisponibilidade(ReservaDTO reservaDTO) {
        // Validar horário de funcionamento
        validarHorario(reservaDTO.getHorario());
        
        // Verificar se a mesa existe
        Mesa mesa = mesaRepository.findById(reservaDTO.getMesaId())
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));
        
        // Verificar se já existe reserva para esta mesa neste horário
        List<Reserva> reservasExistentes = reservaRepository
                .findReservasByMesaAndHorario(reservaDTO.getMesaId(), reservaDTO.getHorario());
        
        if (!reservasExistentes.isEmpty()) {
            throw new RuntimeException("Já existe uma reserva para esta mesa neste horário");
        }
        
        // Verificar disponibilidade da mesa
        if (!"livre".equals(mesa.getStatus())) {
            throw new RuntimeException("Mesa não está disponível");
        }
    }

    private void validarHorario(LocalDateTime horario) {
        if (horario == null) {
            throw new RuntimeException("Horário não pode ser nulo");
        }

        // Validar se o horário está entre 8h e 20h
        LocalTime hora = horario.toLocalTime();
        LocalTime horaAbertura = LocalTime.of(8, 0);
        LocalTime horaFechamento = LocalTime.of(20, 0);
        
        if (hora.isBefore(horaAbertura) || hora.isAfter(horaFechamento)) {
            throw new RuntimeException("Horário de reserva deve ser entre 08:00 e 20:00");
        }

        // Validar se o horário não é no passado
        if (horario.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Não é possível fazer reservas para horários passados");
        }
    }

    public List<ReservaDTO> findReservasByMesaAndData(Long mesaId, LocalDateTime data) {
        return reservaRepository.findReservasByMesaAndData(mesaId, data).stream()
                .map(reservaMapper::toDTO)
                .collect(Collectors.toList());
    }
}