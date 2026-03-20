package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.StationRetrievalDto;
import org.alexmiclea.reptopetrol.model.Station;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StationRetrievalMapper {
    StationRetrievalDto toStationDto(Station station);
    Station toStation(StationRetrievalDto stationDto);

    List<StationRetrievalDto> toStationDtos(List<Station> stations);
    List<Station> toStations(List<StationRetrievalDto> stationDtos);
}