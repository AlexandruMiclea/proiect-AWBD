package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.management.retrieval.composites.InventoryRetrievalDto;
import org.alexmiclea.reptopetrol.model.management.composites.Inventory;
import org.alexmiclea.reptopetrol.repository.management.ProductRepository;
import org.alexmiclea.reptopetrol.repository.management.StoreRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class InventoryRetreivalMapper {
    public abstract InventoryRetrievalDto toInventoryDto(Inventory inventory);

    public abstract List<InventoryRetrievalDto> toInventoryDtos(List<Inventory> inventories);
}