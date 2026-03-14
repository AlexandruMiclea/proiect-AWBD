package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.TransportDto;
import org.alexmiclea.reptopetrol.mapper.TransportMapper;
import org.alexmiclea.reptopetrol.model.Transport;
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
    private final TransportMapper transportMapper;

    @GetMapping("/all")
    public ResponseEntity<List<Transport>> getTransports() {
        return ResponseEntity.ok(transportService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Transport> getTransport(@RequestParam UUID uuid) {
        return ResponseEntity.ok(transportService.getTransportById(uuid));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addTransport(@RequestBody TransportDto transportDto) {
        transportService.addTransport(transportDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddTransports(@RequestBody List<TransportDto> transportDtos) {
        transportService.bulkAddTransports(transportDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateTransport(@RequestBody TransportDto transportDto) {
        transportService.updateTransport(transportDto, transportDto.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Void> deleteTransport(@RequestParam UUID uuid) {
        transportService.deleteTransport(uuid);
        return ResponseEntity.ok().build();
    }
}
