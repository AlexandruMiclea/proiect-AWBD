package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.FuelSupplyRetrievalDto;
import org.alexmiclea.reptopetrol.model.composites.FuelSupply;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FuelSupplyRetrievalMapper {
    FuelSupplyRetrievalDto toFuelSupplyDto(FuelSupply fuelSupply);
    FuelSupply toFuelSupply(FuelSupplyRetrievalDto fuelSupplyDto);

    List<FuelSupplyRetrievalDto> toFuelSupplyDtos(List<FuelSupply> fuelSupplies);
    List<FuelSupply> toFuelSupplies(List<FuelSupplyRetrievalDto> fuelSupplyDtos);
}
