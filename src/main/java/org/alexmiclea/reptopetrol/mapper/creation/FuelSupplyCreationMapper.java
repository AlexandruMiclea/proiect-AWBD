package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.management.creation.composites.FuelSupplyCreationDto;
import org.alexmiclea.reptopetrol.model.management.composites.FuelSupply;
import org.alexmiclea.reptopetrol.repository.management.FuelRepository;
import org.alexmiclea.reptopetrol.repository.management.StationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
    @Mapping(target = "priceChange", expression =
            "java(java.time.Instant.now())")
    public abstract FuelSupply toFuelSupply(FuelSupplyCreationDto fuelSupplyDto);

    public abstract List<FuelSupplyCreationDto> toFuelSupplyDtos(List<FuelSupply> fuelSupplies);
    public abstract List<FuelSupply> toFuelSupplies(List<FuelSupplyCreationDto> fuelSupplyDtos);
}
