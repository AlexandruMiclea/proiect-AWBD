package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.FuelSupplyRetrievalDto;
import org.alexmiclea.reptopetrol.model.composites.FuelSupply;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class FuelSupplyRetrievalMapper {
    public abstract FuelSupplyRetrievalDto toFuelSupplyDto(FuelSupply fuelSupply);
    public abstract FuelSupply toFuelSupply(FuelSupplyRetrievalDto fuelSupplyDto);

    public abstract List<FuelSupplyRetrievalDto> toFuelSupplyDtos(List<FuelSupply> fuelSupplies);
    public abstract List<FuelSupply> toFuelSupplies(List<FuelSupplyRetrievalDto> fuelSupplyDtos);
}
