package org.alexmiclea.reptopetrol.mapper;

import org.alexmiclea.reptopetrol.dto.StationDto;
import org.alexmiclea.reptopetrol.model.Station;
import org.mapstruct.Mapper;
import org.springframework.core.annotation.Order;

import java.util.List;

@Order(1)
@Mapper(componentModel = "spring")
public interface StationMapper {
    StationDto toStationDto(Station station);
    Station toStation(StationDto stationDto);

    List<StationDto> toStationDtos(List<Station> stations);
    List<Station> toStations(List<StationDto> stationDtos);
}