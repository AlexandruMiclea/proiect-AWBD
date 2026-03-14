package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.model.Fuel;
import org.alexmiclea.reptopetrol.service.FuelService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MockController {

    private final FuelService fuelService;

    @GetMapping
    public ResponseEntity<List<Fuel>> getFuels() {
        return ResponseEntity.ok(fuelService.getAll());
    }
}
