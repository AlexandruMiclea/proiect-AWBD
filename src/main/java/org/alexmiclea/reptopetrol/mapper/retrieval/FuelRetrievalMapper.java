package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.FuelRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.keys.FuelSupplyKeyMapper;
import org.alexmiclea.reptopetrol.model.Fuel;
import org.alexmiclea.reptopetrol.repository.ContractRepository;
import org.alexmiclea.reptopetrol.repository.FuelSupplyRepository;
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

    @Mapping(target = "fuelSuppliesIds", expression =
            "java(fuelSupplyKeyMapper.toFuelSupplyKeyDtos(" +
                    "fuel.getFuelSupplies().stream().map(x -> x.getId()).toList())" +
            ")")
    @Mapping(target = "contractIds", expression =
            "java(fuel.getContracts().stream().map(x -> x.getId()).toList())")
    public abstract FuelRetrievalDto toFuelDto(Fuel fuel);

    @Mapping(target = "fuelSupplies", expression =
            "java(fuelSupplyRepository.findAllById(fuelSupplyKeyMapper.toFuelSupplyKeys(fuelDto.getFuelSuppliesIds())))")
    @Mapping(target = "contracts", expression =
            "java(contractRepository.findAllById(fuelDto.getContractIds()))")
    @Mapping(target = "id", ignore = true)
    public abstract Fuel toFuel(FuelRetrievalDto fuelDto);

    public abstract List<FuelRetrievalDto> toFuelDtos(List<Fuel> fuels);
    public abstract List<Fuel> toFuels(List<FuelRetrievalDto> fuelDtos);
}
