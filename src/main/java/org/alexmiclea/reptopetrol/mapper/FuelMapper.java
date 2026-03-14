package org.alexmiclea.reptopetrol.mapper;

import org.alexmiclea.reptopetrol.dto.FuelDto;
import org.alexmiclea.reptopetrol.model.Fuel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FuelMapper {
    FuelDto toFuelDto(Fuel fuel);
    Fuel toFuel(FuelDto fuelDto);

    List<FuelDto> toFuelDtos(List<Fuel> fuels);
    List<Fuel> toFuels(List<FuelDto> fuelDtos);
}
