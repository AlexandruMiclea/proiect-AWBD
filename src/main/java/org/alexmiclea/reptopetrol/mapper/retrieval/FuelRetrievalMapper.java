package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.management.retrieval.FuelRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.keys.FuelSupplyKeyMapper;
import org.alexmiclea.reptopetrol.model.management.Fuel;
import org.alexmiclea.reptopetrol.repository.management.ContractRepository;
import org.alexmiclea.reptopetrol.repository.management.FuelSupplyRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class FuelRetrievalMapper {

    @Autowired
    protected FuelSupplyRepository fuelSupplyRepository;

    @Autowired
    protected ContractRepository contractRepository;

    @Autowired
    protected FuelSupplyKeyMapper fuelSupplyKeyMapper;

    @Mapping(target = "stationIds", expression =
            "java(fuel.getFuelSupplies().stream().map(x -> x.getId().getStationId()).toList())")
    @Mapping(target = "contractIds", expression =
            "java(fuel.getContracts().stream().map(x -> x.getId()).toList())")
    public abstract FuelRetrievalDto toFuelDto(Fuel fuel);

    public abstract List<FuelRetrievalDto> toFuelDtos(List<Fuel> fuels);
}
