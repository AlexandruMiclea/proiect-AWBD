package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.FuelSupplyCreationDto;
import org.alexmiclea.reptopetrol.model.composites.FuelSupply;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class FuelSupplyCreationMapper {
    public abstract FuelSupplyCreationDto toFuelSupplyDto(FuelSupply fuelSupply);
    public abstract FuelSupply toFuelSupply(FuelSupplyCreationDto fuelSupplyDto);

    public abstract List<FuelSupplyCreationDto> toFuelSupplyDtos(List<FuelSupply> fuelSupplies);
    public abstract List<FuelSupply> toFuelSupplies(List<FuelSupplyCreationDto> fuelSupplyDtos);
}
