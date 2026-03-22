package org.alexmiclea.reptopetrol.service;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.FuelCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.FuelRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.FuelCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.FuelRetrievalMapper;
import org.alexmiclea.reptopetrol.model.Fuel;
import org.alexmiclea.reptopetrol.repository.FuelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FuelService {

    private final FuelRepository fuelRepository;
    private final FuelCreationMapper fuelCreationMapper;
    private final FuelRetrievalMapper fuelRetrievalMapper;

    public List<FuelRetrievalDto> getAll() {
        return fuelRetrievalMapper.toFuelDtos(fuelRepository.findAll());
    }

    public FuelRetrievalDto getFuelById(UUID uuid) {
        return fuelRetrievalMapper.toFuelDto(fuelRepository.findById(uuid).orElseThrow());
    }

    public void addFuel(FuelCreationDto fuelDto) {
        Fuel fuel = fuelCreationMapper.toFuel(fuelDto);
        fuelRepository.save(fuel);
    }

    public void bulkAddFuels(List<FuelCreationDto> fuelDtos) {
        List<Fuel> fuels = fuelCreationMapper.toFuels(fuelDtos);
        fuelRepository.saveAll(fuels);
    }

    public void updateFuel(FuelCreationDto fuelDto, UUID uuid) {
        Fuel currentFuel = fuelRepository.getReferenceById(uuid);
        currentFuel.setName(fuelDto.getName());
        currentFuel.setType(fuelDto.getType());
        fuelRepository.save(currentFuel);
    }

    public void deleteFuel(UUID uuid) {
        Fuel fuel = fuelRepository.findById(uuid).orElseThrow();
        fuelRepository.delete(fuel);
    }
}
