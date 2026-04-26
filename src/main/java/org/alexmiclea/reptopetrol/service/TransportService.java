package org.alexmiclea.reptopetrol.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.StoreCreationDto;
import org.alexmiclea.reptopetrol.dto.creation.TransportCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.TransportRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.TransportCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.TransportRetrievalMapper;
import org.alexmiclea.reptopetrol.model.Station;
import org.alexmiclea.reptopetrol.model.Store;
import org.alexmiclea.reptopetrol.model.Transport;
import org.alexmiclea.reptopetrol.repository.StationRepository;
import org.alexmiclea.reptopetrol.repository.TransportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransportService {

    private final TransportRepository transportRepository;
    private final StationRepository stationRepository;
    private final TransportCreationMapper transportCreationMapper;
    private final TransportRetrievalMapper transportRetrievalMapper;

    public List<TransportRetrievalDto> getAll() {
        return transportRetrievalMapper.toTransportDtos(transportRepository.findAll());
    }

    public Optional<TransportRetrievalDto> getTransportById(UUID uuid) {
        if (transportRepository.existsById(uuid)) {
            return Optional.of(transportRetrievalMapper.toTransportDto(transportRepository.findById(uuid).orElseThrow()));
        } else {
            return Optional.empty();
        }
    }

    public void addTransport(TransportCreationDto transportDto) {
        Transport transport = transportCreationMapper.toTransport(transportDto);
        transportRepository.save(transport);
        for (UUID stationId : transportDto.getStationIds()) {
            updateStationTransportID(stationId, transport);
        }
    }

    public void bulkAddTransports(List<TransportCreationDto> transportDtos) {
        List<Transport> transports = transportCreationMapper.toTransports(transportDtos);
        for (TransportCreationDto transportDto : transportDtos) {
            Transport transport = transportCreationMapper.toTransport(transportDto);
            for (UUID stationId : transportDto.getStationIds()) {
                updateStationTransportID(stationId, transport);
            }
        }
        transportRepository.saveAll(transports);
    }

    @Transactional
    public void updateTransport(TransportCreationDto transportDto, UUID uuid) {
        Transport currentTransport = transportRepository.getReferenceById(uuid);
        currentTransport.setCompanyName(transportDto.getCompanyName());
        currentTransport.setCompletionDate(transportDto.getCompletionDate());
        transportRepository.save(currentTransport);
    }

    // Method used to link transportIds to the station
    // TODO add exception handling
    // TODO test that transports get added and not replaced!
    public void updateStationTransportID(UUID stationUuid, Transport transport) {
        Station station = stationRepository.findById(stationUuid).get();
        List<Transport> transports = station.getTransports();
        transports.add(transport);
        station.setTransports(transports);
        stationRepository.save(station);
    }

    public Optional<UUID> deleteTransport(UUID uuid) {
        if (transportRepository.existsById(uuid)) {
            transportRepository.deleteById(uuid);
            return Optional.of(uuid);
        } else {
            return Optional.empty();
        }
    }
}