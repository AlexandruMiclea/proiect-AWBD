package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.management.retrieval.composites.FuelSupplyRetrievalDto;
import org.alexmiclea.reptopetrol.model.management.composites.FuelSupply;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class FuelSupplyRetrievalMapper {
    public abstract FuelSupplyRetrievalDto toFuelSupplyDto(FuelSupply fuelSupply);

    public abstract List<FuelSupplyRetrievalDto> toFuelSupplyDtos(List<FuelSupply> fuelSupplies);
}
