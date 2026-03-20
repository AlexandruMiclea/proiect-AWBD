package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.SupplierCreationDto;
import org.alexmiclea.reptopetrol.model.Supplier;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierCreationMapper {
    SupplierCreationDto toSupplierDto(Supplier supplier);
    Supplier toSupplier(SupplierCreationDto supplierDto);

    List<SupplierCreationDto> toSupplierDtos(List<Supplier> suppliers);
    List<Supplier> toSuppliers(List<SupplierCreationDto> supplierDtos);
}