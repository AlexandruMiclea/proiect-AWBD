package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.InventoryCreationDto;
import org.alexmiclea.reptopetrol.model.composites.Inventory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryCreationMapper {
    InventoryCreationDto toInventoryDto(Inventory inventory);
    Inventory toInventory(InventoryCreationDto inventoryDto);

    List<InventoryCreationDto> toInventoryDtos(List<Inventory> inventories);
    List<Inventory> toInventories(List<InventoryCreationDto> inventoryDtos);
}