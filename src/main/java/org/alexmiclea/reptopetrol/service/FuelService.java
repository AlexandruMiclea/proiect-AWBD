package org.alexmiclea.reptopetrol.service;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.FuelDto;
import org.alexmiclea.reptopetrol.mapper.FuelMapper;
import org.alexmiclea.reptopetrol.model.Fuel;
import org.alexmiclea.reptopetrol.repository.FuelRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FuelService {

    private final FuelRepository fuelRepository;
    private final FuelMapper fuelMapper;

    public List<Fuel> getAll() {
        return fuelRepository.findAll();
    }

    public Fuel getFuelById(UUID uuid) {
        return fuelRepository.findById(uuid).orElseThrow();
    }

    public ResponseEntity<Void> addFuel(FuelDto fuelDto) {
        Fuel fuel = fuelMapper.toFuel(fuelDto);
        fuelRepository.save(fuel);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> bulkAddFuels(List<FuelDto> fuelDtos) {
        List<Fuel> fuels = fuelMapper.toFuels(fuelDtos);
        fuelRepository.saveAll(fuels);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> updateFuel(FuelDto fuelDto, UUID uuid) {
        Fuel currentFuel = fuelRepository.getReferenceById(uuid);
        currentFuel.setName(fuelDto.getName());
        currentFuel.setType(fuelDto.getType());
        fuelRepository.save(currentFuel);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteFuel(UUID uuid) {
        Fuel fuel = fuelRepository.findById(uuid).orElseThrow();
        fuelRepository.delete(fuel);
        return ResponseEntity.ok().build();
    }
}
