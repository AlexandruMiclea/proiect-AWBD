package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.SupplierCreationDto;
import org.alexmiclea.reptopetrol.model.Supplier;
import org.alexmiclea.reptopetrol.repository.ContractRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class SupplierCreationMapper {

    @Autowired
    protected ContractRepository contractRepository;

    @Mapping(target = "contractIds", expression =
            "java(supplier.getContracts().stream().map(x -> x.getId()).toList())")
    public abstract SupplierCreationDto toSupplierDto(Supplier supplier);

//    @Mapping(target = "contracts", expression =
//            "java(contractRepository.findAllById(supplierDto.getContractIds()))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contracts", ignore = true)
    public abstract Supplier toSupplier(SupplierCreationDto supplierDto);

    public abstract List<SupplierCreationDto> toSupplierDtos(List<Supplier> suppliers);
    public abstract List<Supplier> toSuppliers(List<SupplierCreationDto> supplierDtos);
}