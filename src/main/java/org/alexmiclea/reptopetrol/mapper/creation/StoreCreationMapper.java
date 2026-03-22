package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.StoreCreationDto;
import org.alexmiclea.reptopetrol.model.Store;
import org.alexmiclea.reptopetrol.repository.StationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class StoreCreationMapper {

    @Autowired
    protected StationRepository stationRepository;

    @Mapping(target = "stationId", source = "store.station.id")
    public abstract StoreCreationDto toStoreDto(Store store);

    @Mapping(target = "station", expression =
            "java(stationRepository.findById(storeDto.getStationId()).orElseThrow())")
    @Mapping(target = "id", ignore = true)
    public abstract Store toStore(StoreCreationDto storeDto);

    public abstract List<StoreCreationDto> toStoreDtos(List<Store> stores);
    public abstract List<Store> toStores(List<StoreCreationDto> storeDtos);
}