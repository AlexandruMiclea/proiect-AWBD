package org.alexmiclea.reptopetrol.service;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.TransportDto;
import org.alexmiclea.reptopetrol.mapper.TransportMapper;
import org.alexmiclea.reptopetrol.model.Transport;
import org.alexmiclea.reptopetrol.repository.TransportRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransportService {

    private final TransportRepository transportRepository;
    private final TransportMapper transportMapper;

    public List<Transport> getAll() {
        return transportRepository.findAll();
    }

    public Transport getTransportById(UUID uuid) {
        return transportRepository.findById(uuid).orElseThrow();
    }

    public ResponseEntity<Void> addTransport(TransportDto transportDto) {
        Transport transport = transportMapper.toTransport(transportDto);
        transportRepository.save(transport);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> bulkAddTransports(List<TransportDto> transportDtos) {
        List<Transport> transports = transportMapper.toTransports(transportDtos);
        transportRepository.saveAll(transports);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> updateTransport(TransportDto transportDto, UUID uuid) {
        Transport currentTransport = transportRepository.getReferenceById(uuid);
        currentTransport.setCompanyName(transportDto.getCompanyName());
        currentTransport.setCompletionDate(transportDto.getCompletionDate());
        transportRepository.save(currentTransport);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteTransport(UUID uuid) {
        Transport transport = transportRepository.findById(uuid).orElseThrow();
        transportRepository.delete(transport);
        return ResponseEntity.ok().build();
    }
}
