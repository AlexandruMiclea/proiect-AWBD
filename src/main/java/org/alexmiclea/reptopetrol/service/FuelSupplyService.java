package org.alexmiclea.reptopetrol.service;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.FuelSupplyDto;
import org.alexmiclea.reptopetrol.mapper.FuelSupplyMapper;
import org.alexmiclea.reptopetrol.model.composites.FuelSupply;
import org.alexmiclea.reptopetrol.model.composites.keys.FuelSupplyKey;
import org.alexmiclea.reptopetrol.repository.FuelSupplyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuelSupplyService {

    private final FuelSupplyRepository fuelSupplyRepository;
    private final FuelSupplyMapper fuelSupplyMapper;

    public List<FuelSupply> getAll() {
        return fuelSupplyRepository.findAll();
    }

    public FuelSupply getFuelSupplyById(FuelSupplyKey key) {
        return fuelSupplyRepository.findById(key).orElseThrow();
    }

    public ResponseEntity<Void> addFuelSupply(FuelSupplyDto fuelSupplyDto) {
        FuelSupply fuelSupply = fuelSupplyMapper.toFuelSupply(fuelSupplyDto);
        fuelSupplyRepository.save(fuelSupply);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> bulkAddFuelSupplies(List<FuelSupplyDto> fuelSupplyDtos) {
        List<FuelSupply> fuelSupplies = fuelSupplyMapper.toFuelSupplies(fuelSupplyDtos);
        fuelSupplyRepository.saveAll(fuelSupplies);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> updateFuelSupply(FuelSupplyDto fuelSupplyDto, FuelSupplyKey key) {
        FuelSupply currentFuelSupply = fuelSupplyRepository.getReferenceById(key);
        currentFuelSupply.setQuantity(fuelSupplyDto.getQuantity());
        currentFuelSupply.setPrice(fuelSupplyDto.getPrice());
        fuelSupplyRepository.save(currentFuelSupply);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteFuelSupply(FuelSupplyKey key) {
        FuelSupply fuelSupply = fuelSupplyRepository.findById(key).orElseThrow();
        fuelSupplyRepository.delete(fuelSupply);
        return ResponseEntity.ok().build();
    }
}
