package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.StationCreationDto;
import org.alexmiclea.reptopetrol.model.Station;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StationCreationMapper {
    StationCreationDto toStationDto(Station station);
    Station toStation(StationCreationDto stationDto);

    List<StationCreationDto> toStationDtos(List<Station> stations);
    List<Station> toStations(List<StationCreationDto> stationDtos);
}