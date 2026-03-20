package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.StoreCreationDto;
import org.alexmiclea.reptopetrol.model.Store;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoreCreationMapper {
    StoreCreationDto toStoreDto(Store store);
    Store toStore(StoreCreationDto storeDto);

    List<StoreCreationDto> toStoreDtos(List<Store> stores);
    List<Store> toStores(List<StoreCreationDto> storeDtos);
}