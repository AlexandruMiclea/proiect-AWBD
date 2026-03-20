package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.FuelCreationDto;
import org.alexmiclea.reptopetrol.model.Fuel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FuelCreationMapper {
    FuelCreationDto toFuelDto(Fuel fuel);
    Fuel toFuel(FuelCreationDto fuelDto);

    List<FuelCreationDto> toFuelDtos(List<Fuel> fuels);
    List<Fuel> toFuels(List<FuelCreationDto> fuelDtos);
}
