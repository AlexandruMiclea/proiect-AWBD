package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.FuelCreationDto;
import org.alexmiclea.reptopetrol.mapper.keys.FuelSupplyKeyMapper;
import org.alexmiclea.reptopetrol.model.Fuel;
import org.alexmiclea.reptopetrol.repository.ContractRepository;
import org.alexmiclea.reptopetrol.repository.FuelSupplyRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class FuelCreationMapper {

    @Autowired
    protected FuelSupplyRepository fuelSupplyRepository;

    @Autowired
    protected ContractRepository contractRepository;

    @Autowired
    protected FuelSupplyKeyMapper fuelSupplyKeyMapper;

    @Mapping(target = "fuelSuppliesIds", expression =
            "java(fuelSupplyKeyMapper.toFuelSupplyKeyDtos(" +
                    "fuel.getFuelSupplies().stream().map(x -> x.getId()).toList())" +
            ")")
    @Mapping(target = "contractIds", expression =
            "java(fuel.getContracts().stream().map(x -> x.getId()).toList())")
    public abstract FuelCreationDto toFuelDto(Fuel fuel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fuelSupplies", ignore = true)
    @Mapping(target = "contracts", ignore = true)
    public abstract Fuel toFuel(FuelCreationDto fuelDto);

    public abstract List<FuelCreationDto> toFuelDtos(List<Fuel> fuels);
    public abstract List<Fuel> toFuels(List<FuelCreationDto> fuelDtos);
}
