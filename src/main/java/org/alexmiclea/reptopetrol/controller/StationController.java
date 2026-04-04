package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.creation.StationCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.StationRetrievalDto;
import org.alexmiclea.reptopetrol.service.StationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/api/station")
@RequiredArgsConstructor
@Slf4j
public class StationController {

    private final StationService stationService;

    @GetMapping("/all")
    public String getStations(Model model) {
        log.info("GET /all called");
        model.addAttribute("stations", stationService.getAll());
        return "stations/index";
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<StationRetrievalDto> getStation(@PathVariable UUID uuid) {
        log.info("GET /{} called", uuid);
        Optional<StationRetrievalDto> station = stationService.getStationById(uuid);
        log.debug("Database response for GET: {}", station);

        return station.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addStation(@RequestBody @Validated StationCreationDto stationDto) {
        log.info("POST /add called with payload {}", stationDto);
        stationService.addStation(stationDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddStations(@RequestBody @Validated List<StationCreationDto> stationDtos) {
        log.info("POST /bulkAdd called with payload {}", stationDtos);
        stationService.bulkAddStations(stationDtos);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<Void> updateStation(@RequestBody @Validated StationCreationDto stationDto, @PathVariable UUID uuid) {
        log.info("PUT /update called with payload {} for UUID {}", stationDto, uuid);
        stationService.updateStation(stationDto, uuid);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<UUID> deleteStation(@PathVariable UUID uuid) {
        log.info("DELETE /delete called for UUID {}", uuid);
        Optional<UUID> response = stationService.deleteStation(uuid);
        log.debug("Database response for DELETE: {}", response);

        return response.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}