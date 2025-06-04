package com.example.demo.service;

import com.example.demo.Entities.Cliente;
import com.example.demo.Entities.Mesa;
import com.example.demo.Entities.Reserva;
import com.example.demo.dto.ReservaDTO;
import com.example.demo.mapper.ReservaMapper;
import com.example.demo.repository.IClienteRepository;
import com.example.demo.repository.MesaRepository;
import com.example.demo.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ReservaMapper reservaMapper;

    @Autowired
    private MesaService mesaService;

    public List<ReservaDTO> listarTodas() {
        return reservaMapper.toDTOList(reservaRepository.findAll());
    }

    public Optional<ReservaDTO> buscarPorId(Long id) {
        return reservaRepository.findById(id).map(reservaMapper::toDTO);
    }

    public List<ReservaDTO> buscarPorCliente(Long clienteId) {
        return reservaMapper.toDTOList(reservaRepository.findByClienteId(clienteId));
    }

    public List<ReservaDTO> buscarPorMesa(Long mesaId) {
        return reservaMapper.toDTOList(reservaRepository.findByMesaId(mesaId));
    }

    public List<ReservaDTO> buscarPorStatus(String status) {
        return reservaMapper.toDTOList(reservaRepository.findByStatus(status));
    }

    public ReservaDTO criarReserva(ReservaDTO reservaDTO) {
        // Validar data/hora
        if (reservaDTO.getDataReserva() == null) {
            throw new IllegalArgumentException("Data e hora da reserva são obrigatórias");
        }

        LocalDateTime agora = LocalDateTime.now();
        if (reservaDTO.getDataReserva().isBefore(agora)) {
            throw new IllegalArgumentException("A data da reserva deve ser futura");
        }

        // Verificar se não é muito longe no futuro (ex: máximo 90 dias)
        if (reservaDTO.getDataReserva().isAfter(agora.plusDays(90))) {
            throw new IllegalArgumentException("Reservas só podem ser feitas com até 90 dias de antecedência");
        }

        // Verificar horário de funcionamento (exemplo: 08:00 às 23:00)
        LocalTime horaReserva = reservaDTO.getDataReserva().toLocalTime();
        if (horaReserva.isBefore(LocalTime.of(8, 0)) || horaReserva.isAfter(LocalTime.of(23, 0))) {
            throw new IllegalArgumentException("Reservas só podem ser feitas entre 08:00 e 23:00");
        }

        // Verificar se cliente existe
        Cliente cliente = clienteRepository.findById(reservaDTO.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        // Verificar se mesa existe
        Mesa mesa = mesaRepository.findById(reservaDTO.getMesaId())
                .orElseThrow(() -> new IllegalArgumentException("Mesa não encontrada"));

        // Verificar se o cliente já tem reserva ativa para o mesmo dia
        LocalDateTime inicioDia = reservaDTO.getDataReserva().toLocalDate().atStartOfDay();
        LocalDateTime fimDia = inicioDia.plusDays(1).minusSeconds(1);
        
        boolean clienteTemReservaNoDia = reservaRepository.findByClienteId(reservaDTO.getClienteId())
                .stream()
                .anyMatch(r -> "Confirmada".equals(r.getStatus()) && 
                         r.getDataReserva().isAfter(inicioDia) && 
                         r.getDataReserva().isBefore(fimDia));

        if (clienteTemReservaNoDia) {
            throw new IllegalArgumentException("Cliente já possui uma reserva confirmada para este dia");
        }

        // Verificar disponibilidade da mesa no horário
        if (!verificarDisponibilidadeMesa(reservaDTO.getMesaId(), reservaDTO.getDataReserva())) {
            throw new IllegalArgumentException("Mesa não está disponível para este horário");
        }

        // Definir status padrão
        if (reservaDTO.getStatus() == null || reservaDTO.getStatus().isEmpty()) {
            reservaDTO.setStatus("Confirmada");
        }

        Reserva reserva = reservaMapper.toEntity(reservaDTO);
        reserva.setCliente(cliente);
        reserva.setMesa(mesa);
        reserva.setHoraReserva(reservaDTO.getDataReserva().toLocalTime());

        // Atualizar status da mesa para Reservada
        mesaService.atualizarStatus(mesa.getId(), "Reservada");

        Reserva reservaSalva = reservaRepository.save(reserva);
        return reservaMapper.toDTO(reservaSalva);
    }

    private boolean verificarDisponibilidadeMesa(Long mesaId, LocalDateTime dataHoraReserva) {
        // Verificar reservas 2 horas antes e depois (margem de segurança)
        LocalDateTime inicioJanela = dataHoraReserva.minusHours(2);
        LocalDateTime fimJanela = dataHoraReserva.plusHours(2);

        return reservaRepository.findByMesaId(mesaId)
                .stream()
                .noneMatch(r -> "Confirmada".equals(r.getStatus()) && 
                           r.getDataReserva().isAfter(inicioJanela) && 
                           r.getDataReserva().isBefore(fimJanela));
    }

    public ReservaDTO atualizarStatus(Long id, String novoStatus) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada"));

        if (!novoStatus.matches("^(Confirmada|Cancelada|Concluída)$")) {
            throw new IllegalArgumentException("Status inválido. Use: Confirmada, Cancelada ou Concluída");
        }

        String statusAnterior = reserva.getStatus();
        reserva.setStatus(novoStatus);

        // Atualizar status da mesa baseado no novo status da reserva
        if ("Cancelada".equals(novoStatus) || "Concluída".equals(novoStatus)) {
            mesaService.atualizarStatus(reserva.getMesa().getId(), "Livre");
        } else if ("Confirmada".equals(novoStatus) && !"Confirmada".equals(statusAnterior)) {
            // Verificar disponibilidade antes de confirmar
            if (!verificarDisponibilidadeMesa(reserva.getMesa().getId(), reserva.getDataReserva())) {
                throw new IllegalArgumentException("Mesa não está mais disponível para este horário");
            }
            mesaService.atualizarStatus(reserva.getMesa().getId(), "Reservada");
        }

        return reservaMapper.toDTO(reservaRepository.save(reserva));
    }

    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada"));

        // Verificar se pode cancelar (não pode cancelar se já foi concluída)
        if ("Concluída".equals(reserva.getStatus())) {
            throw new IllegalArgumentException("Não é possível cancelar uma reserva já concluída");
        }

        // Liberar a mesa
        mesaService.atualizarStatus(reserva.getMesa().getId(), "Livre");

        reservaRepository.deleteById(id);
    }

    public List<ReservaDTO> buscarReservasAPartirDe(LocalDateTime data) {
        return reservaMapper.toDTOList(reservaRepository.findByDataReservaAfter(data));
    }

    public List<ReservaDTO> buscarReservasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException("Data de início deve ser anterior à data de fim");
        }
        return reservaMapper.toDTOList(reservaRepository.findByDataReservaBetween(inicio, fim));
    }

    public List<ReservaDTO> buscarReservasHoje() {
        LocalDateTime inicioHoje = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime fimHoje = inicioHoje.plusDays(1).minusSeconds(1);
        return buscarReservasPorPeriodo(inicioHoje, fimHoje);
    }

    public List<ReservaDTO> buscarReservasProximasHoras(int horas) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime limite = agora.plusHours(horas);
        return buscarReservasPorPeriodo(agora, limite);
    }
}