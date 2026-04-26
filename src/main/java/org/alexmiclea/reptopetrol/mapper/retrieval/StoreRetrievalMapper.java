package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.StoreRetrievalDto;
import org.alexmiclea.reptopetrol.model.Store;
import org.alexmiclea.reptopetrol.repository.StationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class StoreRetrievalMapper {

    @Autowired
    protected StationRepository stationRepository;

    @Mapping(target = "stationId", source = "store.station.id")
    public abstract StoreRetrievalDto toStoreDto(Store store);

    public abstract List<StoreRetrievalDto> toStoreDtos(List<Store> stores);
}