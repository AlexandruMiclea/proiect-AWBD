package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.FuelRetrievalDto;
import org.alexmiclea.reptopetrol.model.Fuel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FuelRetrievalMapper {
    FuelRetrievalDto toFuelDto(Fuel fuel);
    Fuel toFuel(FuelRetrievalDto fuelDto);

    List<FuelRetrievalDto> toFuelDtos(List<Fuel> fuels);
    List<Fuel> toFuels(List<FuelRetrievalDto> fuelDtos);
}
