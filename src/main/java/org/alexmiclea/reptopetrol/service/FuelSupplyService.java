package org.alexmiclea.reptopetrol.service;

import jakarta.transaction.Transactional;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FuelSupplyService {

    private final FuelSupplyRepository fuelSupplyRepository;
    private final FuelSupplyCreationMapper fuelSupplyCreationMapper;
    private final FuelSupplyRetrievalMapper fuelSupplyRetrievalMapper;

    public List<FuelSupplyRetrievalDto> getAll() {
        return fuelSupplyRetrievalMapper.toFuelSupplyDtos(fuelSupplyRepository.findAll());
    }

    public Optional<FuelSupplyRetrievalDto> getFuelSupplyById(FuelSupplyKey fuelSupplyKey) {
        if (fuelSupplyRepository.existsById(fuelSupplyKey)) {
            return Optional.of(
                    fuelSupplyRetrievalMapper.toFuelSupplyDto(
                            fuelSupplyRepository
                                    .findById(fuelSupplyKey)
                                    .orElseThrow()));
        } else {
            return Optional.empty();
        }
    }

    public void addFuelSupply(FuelSupplyCreationDto fuelSupplyDto) {
        FuelSupply fuelSupply = fuelSupplyCreationMapper.toFuelSupply(fuelSupplyDto);
        fuelSupplyRepository.save(fuelSupply);
    }

    public void bulkAddFuelSupplies(List<FuelSupplyCreationDto> fuelSupplyDtos) {
        List<FuelSupply> fuelSupplies = fuelSupplyCreationMapper.toFuelSupplies(fuelSupplyDtos);
        fuelSupplyRepository.saveAll(fuelSupplies);
    }

    @Transactional
    public void updateFuelSupply(FuelSupplyCreationDto fuelSupplyDto, FuelSupplyKey key) {
        FuelSupply currentFuelSupply = fuelSupplyRepository.getReferenceById(key);
        currentFuelSupply.setQuantity(fuelSupplyDto.getQuantity());
        currentFuelSupply.setPrice(fuelSupplyDto.getPrice());
        fuelSupplyRepository.save(currentFuelSupply);
    }

    public Optional<FuelSupplyKey> deleteFuelSupply(FuelSupplyKey key) {
        if (fuelSupplyRepository.existsById(key)) {
            return Optional.of(key);
        } else {
            return Optional.empty();
        }
    }
}
