package org.alexmiclea.reptopetrol.service;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.StationDto;
import org.alexmiclea.reptopetrol.mapper.StationMapper;
import org.alexmiclea.reptopetrol.model.Station;
import org.alexmiclea.reptopetrol.repository.StationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;
    private final StationMapper stationMapper;

    public List<Station> getAll() {
        return stationRepository.findAll();
    }

    public Station getStationById(UUID uuid) {
        return stationRepository.findById(uuid).orElseThrow();
    }

    public ResponseEntity<Void> addStation(StationDto stationDto) {
        Station station = stationMapper.toStation(stationDto);
        stationRepository.save(station);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> bulkAddStations(List<StationDto> stationDtos) {
        List<Station> stations = stationMapper.toStations(stationDtos);
        stationRepository.saveAll(stations);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> updateStation(StationDto stationDto, UUID uuid) {
        Station currentStation = stationRepository.getReferenceById(uuid);
        currentStation.setName(stationDto.getName());
        currentStation.setAddress(stationDto.getAddress());
        currentStation.setPumpNo(stationDto.getPumpNo());
        stationRepository.save(currentStation);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteStation(UUID uuid) {
        Station station = stationRepository.findById(uuid).orElseThrow();
        stationRepository.delete(station);
        return ResponseEntity.ok().build();
    }
}
