package org.alexmiclea.reptopetrol.service;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.StationCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.StationRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.StationCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.StationRetrievalMapper;
import org.alexmiclea.reptopetrol.model.Station;
import org.alexmiclea.reptopetrol.repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;
    private final StationCreationMapper stationCreationMapper;
    private final StationRetrievalMapper stationRetrievalMapper;

    public List<StationRetrievalDto> getAll() {
        return stationRetrievalMapper.toStationDtos(stationRepository.findAll());
    }

    public StationRetrievalDto getStationById(UUID uuid) {
        return stationRetrievalMapper.toStationDto(stationRepository.findById(uuid).orElseThrow());
    }

    public void addStation(StationCreationDto stationDto) {
        Station station = stationCreationMapper.toStation(stationDto);
        stationRepository.save(station);
    }

    public void bulkAddStations(List<StationCreationDto> stationDtos) {
        List<Station> stations = stationCreationMapper.toStations(stationDtos);
        stationRepository.saveAll(stations);
    }

    public void updateStation(StationCreationDto stationDto, UUID uuid) {
        Station currentStation = stationRepository.getReferenceById(uuid);
        currentStation.setName(stationDto.getName());
        currentStation.setAddress(stationDto.getAddress());
        currentStation.setPumpNo(stationDto.getPumpNo());
        stationRepository.save(currentStation);
    }

    public void deleteStation(UUID uuid) {
        Station station = stationRepository.findById(uuid).orElseThrow();
        stationRepository.delete(station);
    }
}
