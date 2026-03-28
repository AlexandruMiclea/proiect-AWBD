package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.InventoryCreationDto;
import org.alexmiclea.reptopetrol.model.composites.Inventory;
import org.alexmiclea.reptopetrol.repository.FuelSupplyRepository;
import org.alexmiclea.reptopetrol.repository.ProductRepository;
import org.alexmiclea.reptopetrol.repository.StoreRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

// TODO add instant for creating

@Mapper(componentModel = "spring")
public abstract class InventoryCreationMapper {
    @Autowired
    protected StoreRepository storeRepository;

    @Autowired
    protected ProductRepository productRepository;

    public abstract InventoryCreationDto toInventoryDto(Inventory inventory);

    @Mapping(target = "product", expression =
            "java(productRepository.findById(inventoryDto.getId().getProductId()).orElseThrow())")
    @Mapping(target = "store", expression =
            "java(storeRepository.findById(inventoryDto.getId().getStoreId()).orElseThrow())")
    public abstract Inventory toInventory(InventoryCreationDto inventoryDto);

    public abstract List<InventoryCreationDto> toInventoryDtos(List<Inventory> inventories);
    public abstract List<Inventory> toInventories(List<InventoryCreationDto> inventoryDtos);
}