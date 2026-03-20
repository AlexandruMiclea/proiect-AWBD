package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.StoreRetrievalDto;
import org.alexmiclea.reptopetrol.model.Store;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoreRetrievalMapper {
    StoreRetrievalDto toStoreDto(Store store);
    Store toStore(StoreRetrievalDto storeDto);

    List<StoreRetrievalDto> toStoreDtos(List<Store> stores);
    List<Store> toStores(List<StoreRetrievalDto> storeDtos);
}