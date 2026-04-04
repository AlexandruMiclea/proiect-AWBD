package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.creation.TransportCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.TransportRetrievalDto;
import org.alexmiclea.reptopetrol.service.TransportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/api/transport")
@RequiredArgsConstructor
@Slf4j
public class TransportController {

    private final TransportService transportService;

    @GetMapping("/all")
    public String getTransports(Model model) {
        log.info("GET /all called");
        model.addAttribute("transports", transportService.getAll());
        return "transports/index";
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TransportRetrievalDto> getTransport(@PathVariable UUID uuid) {
        log.info("GET /{} called", uuid);
        Optional<TransportRetrievalDto> transport = transportService.getTransportById(uuid);
        log.debug("Database response for GET: {}", transport);

        return transport.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addTransport(@RequestBody @Validated TransportCreationDto transportDto) {
        log.info("POST /add called with payload {}", transportDto);
        transportService.addTransport(transportDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddTransports(@RequestBody @Validated List<TransportCreationDto> transportDtos) {
        log.info("POST /bulkAdd called with payload {}", transportDtos);
        transportService.bulkAddTransports(transportDtos);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<Void> updateTransport(@RequestBody @Validated TransportCreationDto transportDto, @PathVariable UUID uuid) {
        log.info("PUT /update called with payload {} for UUID {}", transportDto, uuid);
        transportService.updateTransport(transportDto, uuid);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<UUID> deleteTransport(@PathVariable UUID uuid) {
        log.info("DELETE /delete called for UUID {}", uuid);
        Optional<UUID> response = transportService.deleteTransport(uuid);
        log.debug("Database response for DELETE: {}", response);

        return response.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}