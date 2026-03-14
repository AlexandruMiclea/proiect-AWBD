package org.alexmiclea.reptopetrol.mapper;

import org.alexmiclea.reptopetrol.dto.StoreDto;
import org.alexmiclea.reptopetrol.model.Store;
import org.mapstruct.Mapper;
import org.springframework.core.annotation.Order;

import java.util.List;

@Order(1)
@Mapper(componentModel = "spring")
public interface StoreMapper {
    StoreDto toStoreDto(Store store);
    Store toStore(StoreDto storeDto);

    List<StoreDto> toStoreDtos(List<Store> stores);
    List<Store> toStores(List<StoreDto> storeDtos);
}