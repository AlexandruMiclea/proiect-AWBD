package org.alexmiclea.reptopetrol.mapper.keys;

import org.alexmiclea.reptopetrol.dto.keys.InventoryKeyDto;
import org.alexmiclea.reptopetrol.model.composites.keys.InventoryKey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class InventoryKeyMapper {

    @Mapping(target = "productId", source = "inventoryKey.productId")
    @Mapping(target = "storeId", source = "inventoryKey.storeId")
    public abstract InventoryKeyDto toInventoryKeyDto(InventoryKey inventoryKey);

    @Mapping(target = "productId", source = "inventoryKeyDto.productId")
    @Mapping(target = "storeId", source = "inventoryKeyDto.storeId")
    public abstract InventoryKey toInventoryKey(InventoryKeyDto inventoryKeyDto);

    public abstract List<InventoryKeyDto> toInventoryKeyDtos(List<InventoryKey> inventoryKeys);
    public abstract List<InventoryKey> toInventoryKeys(List<InventoryKeyDto> inventoryKeyDtos);
}
