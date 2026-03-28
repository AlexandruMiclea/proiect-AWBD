package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.FuelSupplyCreationDto;
import org.alexmiclea.reptopetrol.model.composites.FuelSupply;
import org.alexmiclea.reptopetrol.repository.FuelRepository;
import org.alexmiclea.reptopetrol.repository.ProductRepository;
import org.alexmiclea.reptopetrol.repository.StationRepository;
import org.alexmiclea.reptopetrol.repository.StoreRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

// TODO add instant for creating

@Mapper(componentModel = "spring")
public abstract class FuelSupplyCreationMapper {
    @Autowired
    protected FuelRepository fuelRepository;

    @Autowired
    protected StationRepository stationRepository;

    public abstract FuelSupplyCreationDto toFuelSupplyDto(FuelSupply fuelSupply);

    @Mapping(target = "fuel", expression =
            "java(fuelRepository.findById(fuelSupplyDto.getId().getFuelId()).orElseThrow())")
    @Mapping(target = "station", expression =
            "java(stationRepository.findById(fuelSupplyDto.getId().getStationId()).orElseThrow())")
    public abstract FuelSupply toFuelSupply(FuelSupplyCreationDto fuelSupplyDto);

    public abstract List<FuelSupplyCreationDto> toFuelSupplyDtos(List<FuelSupply> fuelSupplies);
    public abstract List<FuelSupply> toFuelSupplies(List<FuelSupplyCreationDto> fuelSupplyDtos);
}
