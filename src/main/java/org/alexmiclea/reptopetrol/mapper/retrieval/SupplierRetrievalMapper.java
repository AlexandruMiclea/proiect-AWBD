package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.SupplierRetrievalDto;
import org.alexmiclea.reptopetrol.model.Supplier;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierRetrievalMapper {
    SupplierRetrievalDto toSupplierDto(Supplier supplier);
    Supplier toSupplier(SupplierRetrievalDto supplierDto);

    List<SupplierRetrievalDto> toSupplierDtos(List<Supplier> suppliers);
    List<Supplier> toSuppliers(List<SupplierRetrievalDto> supplierDtos);
}