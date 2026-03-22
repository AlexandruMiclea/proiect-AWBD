package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.InventoryRetrievalDto;
import org.alexmiclea.reptopetrol.model.composites.Inventory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class InventoryRetreivalMapper {
    public abstract InventoryRetrievalDto toInventoryDto(Inventory inventory);
    public abstract Inventory toInventory(InventoryRetrievalDto inventoryDto);

    public abstract List<InventoryRetrievalDto> toInventoryDtos(List<Inventory> inventories);
    public abstract List<Inventory> toInventories(List<InventoryRetrievalDto> inventoryDtos);
}