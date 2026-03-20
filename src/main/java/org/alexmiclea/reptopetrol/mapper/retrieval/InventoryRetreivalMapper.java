package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.InventoryRetrievalDto;
import org.alexmiclea.reptopetrol.model.composites.Inventory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryRetreivalMapper {
    InventoryRetrievalDto toInventoryDto(Inventory inventory);
    Inventory toInventory(InventoryRetrievalDto inventoryDto);

    List<InventoryRetrievalDto> toInventoryDtos(List<Inventory> inventories);
    List<Inventory> toInventories(List<InventoryRetrievalDto> inventoryDtos);
}