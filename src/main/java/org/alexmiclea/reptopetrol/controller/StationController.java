package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.StationDto;
import org.alexmiclea.reptopetrol.mapper.StationMapper;
import org.alexmiclea.reptopetrol.model.Station;
import org.alexmiclea.reptopetrol.service.StationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;
    private final StationMapper stationMapper;

    @GetMapping("/all")
    public ResponseEntity<List<Station>> getStations() {
        return ResponseEntity.ok(stationService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Station> getStation(@RequestParam UUID uuid) {
        return ResponseEntity.ok(stationService.getStationById(uuid));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addStation(@RequestBody StationDto stationDto) {
        stationService.addStation(stationDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddStations(@RequestBody List<StationDto> stationDtos) {
        stationService.bulkAddStations(stationDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateStation(@RequestBody StationDto stationDto) {
        stationService.updateStation(stationDto, stationDto.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Void> deleteStation(@RequestParam UUID uuid) {
        stationService.deleteStation(uuid);
        return ResponseEntity.ok().build();
    }
}
