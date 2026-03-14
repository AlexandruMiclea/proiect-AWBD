package org.alexmiclea.reptopetrol.mapper;

import org.alexmiclea.reptopetrol.dto.SupplierDto;
import org.alexmiclea.reptopetrol.model.Supplier;
import org.mapstruct.Mapper;
import org.springframework.core.annotation.Order;

import java.util.List;

@Order(1)
@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierDto toSupplierDto(Supplier supplier);
    Supplier toSupplier(SupplierDto supplierDto);

    List<SupplierDto> toSupplierDtos(List<Supplier> suppliers);
    List<Supplier> toSuppliers(List<SupplierDto> supplierDtos);
}