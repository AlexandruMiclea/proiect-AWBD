package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.FuelSupplyCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.FuelSupplyRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.FuelSupplyCreationMapper;
import org.alexmiclea.reptopetrol.model.composites.keys.FuelSupplyKey;
import org.alexmiclea.reptopetrol.service.FuelSupplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fuel-supplies")
@RequiredArgsConstructor
public class FuelSupplyController {

    private final FuelSupplyService fuelSupplyService;

    @GetMapping("/all")
    public ResponseEntity<List<FuelSupplyRetrievalDto>> getFuelSupplies() {
        return ResponseEntity.ok(fuelSupplyService.getAll());
    }

    @GetMapping("/get")
    public ResponseEntity<FuelSupplyRetrievalDto> getFuelSupply(@RequestBody FuelSupplyKey key) {
        return ResponseEntity.ok(fuelSupplyService.getFuelSupplyById(key));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addFuelSupply(@RequestBody FuelSupplyCreationDto fuelSupplyDto) {
        fuelSupplyService.addFuelSupply(fuelSupplyDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddFuelSupplies(@RequestBody List<FuelSupplyCreationDto> fuelSupplyDtos) {
        fuelSupplyService.bulkAddFuelSupplies(fuelSupplyDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateFuelSupply(@RequestBody FuelSupplyCreationDto fuelSupplyDto) {
        fuelSupplyService.updateFuelSupply(fuelSupplyDto, fuelSupplyDto.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFuelSupply(@RequestBody FuelSupplyKey key) {
        fuelSupplyService.deleteFuelSupply(key);
        return ResponseEntity.ok().build();
    }
}
