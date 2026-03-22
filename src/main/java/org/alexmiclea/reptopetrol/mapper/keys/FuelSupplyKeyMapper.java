package org.alexmiclea.reptopetrol.mapper.keys;

import org.alexmiclea.reptopetrol.dto.keys.FuelSupplyKeyDto;
import org.alexmiclea.reptopetrol.model.composites.keys.FuelSupplyKey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class FuelSupplyKeyMapper {
    @Mapping(target = "stationId", source = "fuelSupplyKey.stationId")
    @Mapping(target = "fuelId", source = "fuelSupplyKey.fuelId")
    public abstract FuelSupplyKeyDto toFuelSupplyKeyDto(FuelSupplyKey fuelSupplyKey);

    @Mapping(target = "stationId", source = "fuelSupplyKeyDto.stationId")
    @Mapping(target = "fuelId", source = "fuelSupplyKeyDto.fuelId")
    public abstract FuelSupplyKey toFuelSupplyKey(FuelSupplyKeyDto fuelSupplyKeyDto);

    public abstract List<FuelSupplyKeyDto> toFuelSupplyKeyDtos(List<FuelSupplyKey> fuelSupplyKeys);
    public abstract List<FuelSupplyKey> toFuelSupplyKeys(List<FuelSupplyKeyDto> fuelSupplyKeyDtos);
}
