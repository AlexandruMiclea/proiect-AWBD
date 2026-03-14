package org.alexmiclea.reptopetrol.mapper;

import org.alexmiclea.reptopetrol.dto.StationDto;
import org.alexmiclea.reptopetrol.model.Station;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StationMapper {
    StationDto toStationDto(Station station);
    Station toStation(StationDto stationDto);

    List<StationDto> toStationDtos(List<Station> stations);
    List<Station> toStations(List<StationDto> stationDtos);
}