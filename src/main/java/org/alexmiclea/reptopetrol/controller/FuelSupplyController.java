package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.FuelSupplyDto;
import org.alexmiclea.reptopetrol.mapper.FuelSupplyMapper;
import org.alexmiclea.reptopetrol.model.composites.FuelSupply;
import org.alexmiclea.reptopetrol.model.composites.keys.FuelSupplyKey;
import org.alexmiclea.reptopetrol.service.FuelSupplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fuel-supplies")
@RequiredArgsConstructor
public class FuelSupplyController {

    private final FuelSupplyService fuelSupplyService;
    private final FuelSupplyMapper fuelSupplyMapper;

    @GetMapping("/all")
    public ResponseEntity<List<FuelSupply>> getFuelSupplies() {
        return ResponseEntity.ok(fuelSupplyService.getAll());
    }

    @GetMapping("/get")
    public ResponseEntity<FuelSupply> getFuelSupply(@RequestBody FuelSupplyKey key) {
        return ResponseEntity.ok(fuelSupplyService.getFuelSupplyById(key));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addFuelSupply(@RequestBody FuelSupplyDto fuelSupplyDto) {
        fuelSupplyService.addFuelSupply(fuelSupplyDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddFuelSupplies(@RequestBody List<FuelSupplyDto> fuelSupplyDtos) {
        fuelSupplyService.bulkAddFuelSupplies(fuelSupplyDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateFuelSupply(@RequestBody FuelSupplyDto fuelSupplyDto) {
        fuelSupplyService.updateFuelSupply(fuelSupplyDto, fuelSupplyDto.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFuelSupply(@RequestBody FuelSupplyKey key) {
        fuelSupplyService.deleteFuelSupply(key);
        return ResponseEntity.ok().build();
    }
}
