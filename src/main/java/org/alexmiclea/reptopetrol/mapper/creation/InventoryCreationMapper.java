package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.InventoryCreationDto;
import org.alexmiclea.reptopetrol.model.composites.Inventory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class InventoryCreationMapper {
    public abstract InventoryCreationDto toInventoryDto(Inventory inventory);
    public abstract Inventory toInventory(InventoryCreationDto inventoryDto);

    public abstract List<InventoryCreationDto> toInventoryDtos(List<Inventory> inventories);
    public abstract List<Inventory> toInventories(List<InventoryCreationDto> inventoryDtos);
}