package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.FuelSupplyCreationDto;
import org.alexmiclea.reptopetrol.model.composites.FuelSupply;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FuelSupplyCreationMapper {
    FuelSupplyCreationDto toFuelSupplyDto(FuelSupply fuelSupply);
    FuelSupply toFuelSupply(FuelSupplyCreationDto fuelSupplyDto);

    List<FuelSupplyCreationDto> toFuelSupplyDtos(List<FuelSupply> fuelSupplies);
    List<FuelSupply> toFuelSupplies(List<FuelSupplyCreationDto> fuelSupplyDtos);
}
