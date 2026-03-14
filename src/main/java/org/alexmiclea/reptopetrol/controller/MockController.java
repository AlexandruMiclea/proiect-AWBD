package org.alexmiclea.reptopetrol.controller;

import org.alexmiclea.reptopetrol.model.Fuel;
import org.alexmiclea.reptopetrol.repository.FuelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MockController {

    private final FuelRepository fuelRepository;

    @Autowired
    public MockController(FuelRepository fuelRepository) {
        this.fuelRepository = fuelRepository;
    }

    @GetMapping
    public ResponseEntity<List<Fuel>> getFuels() {
        return ResponseEntity.ok(fuelRepository.findAll());
    }
}
