package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.FuelDto;
import org.alexmiclea.reptopetrol.mapper.FuelMapper;
import org.alexmiclea.reptopetrol.model.Fuel;
import org.alexmiclea.reptopetrol.service.FuelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fuels")
@RequiredArgsConstructor
public class FuelController {

    private final FuelService fuelService;
    private final FuelMapper fuelMapper;

    @GetMapping("/all")
    public ResponseEntity<List<Fuel>> getFuels() {
        return ResponseEntity.ok(fuelService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Fuel> getFuel(@RequestParam UUID uuid) {
        return ResponseEntity.ok(fuelService.getFuelById(uuid));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addFuel(@RequestBody FuelDto fuelDto) {
        fuelService.addFuel(fuelDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddFuels(@RequestBody List<FuelDto> fuelDtos) {
        fuelService.bulkAddFuels(fuelDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateFuel(@RequestBody FuelDto fuelDto) {
        fuelService.updateFuel(fuelDto, fuelDto.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Void> deleteFuel(@RequestParam UUID uuid) {
        fuelService.deleteFuel(uuid);
        return ResponseEntity.ok().build();
    }
}
