package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.ContractRetrievalDto;
import org.alexmiclea.reptopetrol.model.Contract;
import org.alexmiclea.reptopetrol.repository.FuelRepository;
import org.alexmiclea.reptopetrol.repository.SupplierRepository;
import org.alexmiclea.reptopetrol.repository.TransportRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ContractRetrievalMapper {

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
    public abstract ContractRetrievalDto toContractDto(Contract contract);

    @Mapping(target = "supplier", expression =
            "java(supplierRepository.findById(contractDto.getSupplierId()).orElseThrow())")
    @Mapping(target = "fuels", expression =
            "java(fuelRepository.findAllById(contractDto.getFuelIds()))")
    @Mapping(target = "transports", expression =
            "java(transportRepository.findAllById(contractDto.getTransportIds()))")
    @Mapping(target = "id", ignore = true)
    public abstract Contract toContract(ContractRetrievalDto contractDto);

    public abstract List<ContractRetrievalDto> toContractDtos(List<Contract> contracts);
    public abstract List<Contract> toContracts(List<ContractRetrievalDto> contractDtos);
}