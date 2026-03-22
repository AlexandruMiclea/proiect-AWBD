package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.StationCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.StationRetrievalDto;
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

    @GetMapping("/all")
    public ResponseEntity<List<StationRetrievalDto>> getStations() {
        return ResponseEntity.ok(stationService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<StationRetrievalDto> getStation(@PathVariable UUID uuid) {
        return ResponseEntity.ok(stationService.getStationById(uuid));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addStation(@RequestBody StationCreationDto stationDto) {
        stationService.addStation(stationDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddStations(@RequestBody List<StationCreationDto> stationDtos) {
        stationService.bulkAddStations(stationDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<Void> updateStation(@RequestBody StationCreationDto stationDto, @PathVariable UUID uuid) {
        stationService.updateStation(stationDto, uuid);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Void> deleteStation(@PathVariable UUID uuid) {
        stationService.deleteStation(uuid);
        return ResponseEntity.ok().build();
    }
}
