package org.alexmiclea.reptopetrol.service;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.FuelSupplyCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.FuelSupplyRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.FuelSupplyCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.FuelSupplyRetrievalMapper;
import org.alexmiclea.reptopetrol.model.composites.FuelSupply;
import org.alexmiclea.reptopetrol.model.composites.keys.FuelSupplyKey;
import org.alexmiclea.reptopetrol.repository.FuelSupplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuelSupplyService {

    private final FuelSupplyRepository fuelSupplyRepository;
    private final FuelSupplyCreationMapper fuelSupplyCreationMapper;
    private final FuelSupplyRetrievalMapper fuelSupplyRetrievalMapper;

    public List<FuelSupplyRetrievalDto> getAll() {
        return fuelSupplyRetrievalMapper.toFuelSupplyDtos(fuelSupplyRepository.findAll());
    }

    public FuelSupplyRetrievalDto getFuelSupplyById(FuelSupplyKey key) {
        return fuelSupplyRetrievalMapper.toFuelSupplyDto(fuelSupplyRepository.findById(key).orElseThrow());
    }

    public void addFuelSupply(FuelSupplyCreationDto fuelSupplyDto) {
        FuelSupply fuelSupply = fuelSupplyCreationMapper.toFuelSupply(fuelSupplyDto);
        fuelSupplyRepository.save(fuelSupply);
    }

    public void bulkAddFuelSupplies(List<FuelSupplyCreationDto> fuelSupplyDtos) {
        List<FuelSupply> fuelSupplies = fuelSupplyCreationMapper.toFuelSupplies(fuelSupplyDtos);
        fuelSupplyRepository.saveAll(fuelSupplies);
    }

    public void updateFuelSupply(FuelSupplyCreationDto fuelSupplyDto, FuelSupplyKey key) {
        FuelSupply currentFuelSupply = fuelSupplyRepository.getReferenceById(key);
        currentFuelSupply.setQuantity(fuelSupplyDto.getQuantity());
        currentFuelSupply.setPrice(fuelSupplyDto.getPrice());
        fuelSupplyRepository.save(currentFuelSupply);
    }

    public void deleteFuelSupply(FuelSupplyKey key) {
        FuelSupply fuelSupply = fuelSupplyRepository.findById(key).orElseThrow();
        fuelSupplyRepository.delete(fuelSupply);
    }
}
