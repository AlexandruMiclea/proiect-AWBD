package org.alexmiclea.reptopetrol.mapper;

import org.alexmiclea.reptopetrol.dto.InventoryDto;
import org.alexmiclea.reptopetrol.model.composites.Inventory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StoreMapper.class, ProductMapper.class})
public interface InventoryMapper {
    InventoryDto toInventoryDto(Inventory inventory);
    Inventory toInventory(InventoryDto inventoryDto);

    List<InventoryDto> toInventoryDtos(List<Inventory> inventories);
    List<Inventory> toInventories(List<InventoryDto> inventoryDtos);
}