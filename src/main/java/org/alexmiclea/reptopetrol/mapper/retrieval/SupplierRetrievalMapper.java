package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.SupplierRetrievalDto;
import org.alexmiclea.reptopetrol.model.Supplier;
import org.alexmiclea.reptopetrol.repository.ContractRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class SupplierRetrievalMapper {

    @Autowired
    protected ContractRepository contractRepository;

    @Mapping(target = "contractIds", expression =
            "java(supplier.getContracts().stream().map(x -> x.getId()).toList())")
    public abstract SupplierRetrievalDto toSupplierDto(Supplier supplier);

    @Mapping(target = "contracts", expression =
            "java(contractRepository.findAllById(supplierDto.getContractIds()))")
    @Mapping(target = "id", ignore = true)
    public abstract Supplier toSupplier(SupplierRetrievalDto supplierDto);

    public abstract List<SupplierRetrievalDto> toSupplierDtos(List<Supplier> suppliers);
    public abstract List<Supplier> toSuppliers(List<SupplierRetrievalDto> supplierDtos);
}