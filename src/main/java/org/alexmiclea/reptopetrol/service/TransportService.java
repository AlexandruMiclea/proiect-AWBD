package org.alexmiclea.reptopetrol.service;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.TransportCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.TransportRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.TransportCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.TransportRetrievalMapper;
import org.alexmiclea.reptopetrol.model.Transport;
import org.alexmiclea.reptopetrol.repository.TransportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransportService {

    private final TransportRepository transportRepository;
    private final TransportCreationMapper transportCreationMapper;
    private final TransportRetrievalMapper transportRetrievalMapper;

    public List<TransportRetrievalDto> getAll() {
        return transportRetrievalMapper.toTransportDtos(transportRepository.findAll());
    }

    public TransportRetrievalDto getTransportById(UUID uuid) {
        return transportRetrievalMapper.toTransportDto(transportRepository.findById(uuid).orElseThrow());
    }

    public void addTransport(TransportCreationDto transportDto) {
        Transport transport = transportCreationMapper.toTransport(transportDto);
        transportRepository.save(transport);
    }

    public void bulkAddTransports(List<TransportCreationDto> transportDtos) {
        List<Transport> transports = transportCreationMapper.toTransports(transportDtos);
        transportRepository.saveAll(transports);
    }

    public void updateTransport(TransportCreationDto transportDto, UUID uuid) {
        Transport currentTransport = transportRepository.getReferenceById(uuid);
        currentTransport.setCompanyName(transportDto.getCompanyName());
        currentTransport.setCompletionDate(transportDto.getCompletionDate());
        transportRepository.save(currentTransport);
    }

    public void deleteTransport(UUID uuid) {
        Transport transport = transportRepository.findById(uuid).orElseThrow();
        transportRepository.delete(transport);
    }
}
