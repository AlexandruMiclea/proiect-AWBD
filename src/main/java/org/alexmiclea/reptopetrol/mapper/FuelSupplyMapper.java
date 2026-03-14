package org.alexmiclea.reptopetrol.mapper;

import org.alexmiclea.reptopetrol.dto.FuelSupplyDto;
import org.alexmiclea.reptopetrol.model.composites.FuelSupply;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {FuelMapper.class, StationMapper.class})
public interface FuelSupplyMapper {
    FuelSupplyDto toFuelSupplyDto(FuelSupply fuelSupply);
    FuelSupply toFuelSupply(FuelSupplyDto fuelSupplyDto);

    List<FuelSupplyDto> toFuelSupplyDtos(List<FuelSupply> fuelSupplies);
    List<FuelSupply> toFuelSupplies(List<FuelSupplyDto> fuelSupplyDtos);
}
