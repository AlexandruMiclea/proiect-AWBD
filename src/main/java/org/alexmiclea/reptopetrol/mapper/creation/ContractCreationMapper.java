package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.ContractCreationDto;
import org.alexmiclea.reptopetrol.model.Contract;
import org.alexmiclea.reptopetrol.repository.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ContractCreationMapper {

    @Autowired
    protected SupplierRepository supplierRepository;

    @Autowired
    protected TransportRepository transportRepository;

    @Autowired
    protected FuelRepository fuelRepository;

    @Mapping(target = "supplierId", source = "contract.supplier.id")
    @Mapping(target = "fuelIds", expression =
            "java(contract.getFuels().stream().map(x -> x.getId()).toList())")
    @Mapping(target = "transportIds", expression =
            "java(contract.getTransports().stream().map(x -> x.getId()).toList())")
    public abstract ContractCreationDto toContractDto(Contract contract);

    @Mapping(target = "supplier", expression =
            "java(supplierRepository.findById(contractDto.getSupplierId()).orElseThrow())")
    @Mapping(target = "fuels", expression =
            "java(fuelRepository.findAllById(contractDto.getFuelIds()))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "transports", ignore = true)
    public abstract Contract toContract(ContractCreationDto contractDto);

    public abstract List<ContractCreationDto> toContractDtos(List<Contract> contracts);
    public abstract List<Contract> toContracts(List<ContractCreationDto> contractDtos);
}
