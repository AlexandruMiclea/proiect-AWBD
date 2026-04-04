package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.creation.FuelSupplyCreationDto;
import org.alexmiclea.reptopetrol.dto.keys.FuelSupplyKeyDto;
import org.alexmiclea.reptopetrol.dto.retrieval.FuelSupplyRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.keys.FuelSupplyKeyMapper;
import org.alexmiclea.reptopetrol.model.keys.FuelSupplyKey;
import org.alexmiclea.reptopetrol.service.FuelService;
import org.alexmiclea.reptopetrol.service.FuelSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/fuelSupply")
@RequiredArgsConstructor
@Slf4j
public class FuelSupplyController {

    private final FuelSupplyService fuelSupplyService;

    @Autowired
    private final FuelSupplyKeyMapper fuelSupplyKeyMapper;

    @GetMapping("/all")
    public String getFuelSupplies(Model model) {
        log.info("GET /all called");
        model.addAttribute("fuelSupplies", fuelSupplyService.getAll());
        return "fuelSupplies/index";
    }

    @GetMapping
    public ResponseEntity<FuelSupplyRetrievalDto> getFuelSupply(@RequestBody FuelSupplyKeyDto key) {
        log.info("GET called with composed key {}", key);
        FuelSupplyKey fuelSupplyKey = fuelSupplyKeyMapper.toFuelSupplyKey(key);
        Optional<FuelSupplyRetrievalDto> fuelSupply = fuelSupplyService.getFuelSupplyById(fuelSupplyKey);
        log.debug("Database response for GET: {}", fuelSupply);

        return fuelSupply.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addFuelSupply(@RequestBody FuelSupplyCreationDto fuelSupplyDto) {
        log.info("POST /add called with payload {}", fuelSupplyDto);
        fuelSupplyService.addFuelSupply(fuelSupplyDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddFuelSupplies(@RequestBody List<FuelSupplyCreationDto> fuelSupplyDtos) {
        log.info("POST /bulkAdd called with payload {}", fuelSupplyDtos);
        fuelSupplyService.bulkAddFuelSupplies(fuelSupplyDtos);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateFuelSupply(@RequestBody FuelSupplyCreationDto fuelSupplyDto) {
        log.info("PUT /update called with payload {}", fuelSupplyDto);
        fuelSupplyService.updateFuelSupply(fuelSupplyDto, fuelSupplyDto.getId());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<FuelSupplyKey> deleteFuelSupply(@RequestBody FuelSupplyKeyDto key) {
        log.info("DELETE called with composed key {}", key);
        FuelSupplyKey fuelSupplyKey = fuelSupplyKeyMapper.toFuelSupplyKey(key);
        Optional<FuelSupplyKey> fuelSupply = fuelSupplyService.deleteFuelSupply(fuelSupplyKey);
        log.debug("Database response for DELETE: {}", fuelSupply);

        return fuelSupply.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
