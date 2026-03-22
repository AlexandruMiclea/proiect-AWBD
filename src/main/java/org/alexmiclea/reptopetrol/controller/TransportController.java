package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.TransportCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.TransportRetrievalDto;
import org.alexmiclea.reptopetrol.service.TransportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transports")
@RequiredArgsConstructor
public class TransportController {

    private final TransportService transportService;

    @GetMapping("/all")
    public ResponseEntity<List<TransportRetrievalDto>> getTransports() {
        return ResponseEntity.ok(transportService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TransportRetrievalDto> getTransport(@PathVariable UUID uuid) {
        return ResponseEntity.ok(transportService.getTransportById(uuid));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addTransport(@RequestBody TransportCreationDto transportDto) {
        transportService.addTransport(transportDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddTransports(@RequestBody List<TransportCreationDto> transportDtos) {
        transportService.bulkAddTransports(transportDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<Void> updateTransport(@RequestBody TransportCreationDto transportDto, @PathVariable UUID uuid) {
        transportService.updateTransport(transportDto, uuid);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Void> deleteTransport(@PathVariable UUID uuid) {
        transportService.deleteTransport(uuid);
        return ResponseEntity.ok().build();
    }
}
