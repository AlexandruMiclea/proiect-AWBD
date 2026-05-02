package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.management.retrieval.StationRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.keys.FuelSupplyKeyMapper;
import org.alexmiclea.reptopetrol.model.management.Station;
import org.alexmiclea.reptopetrol.repository.management.EmployeeRepository;
import org.alexmiclea.reptopetrol.repository.management.FuelSupplyRepository;
import org.alexmiclea.reptopetrol.repository.management.StoreRepository;
import org.alexmiclea.reptopetrol.repository.management.TransportRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class StationRetrievalMapper {

    @Autowired
    protected FuelSupplyRepository fuelSupplyRepository;

    @Autowired
    protected EmployeeRepository employeeRepository;

    @Autowired
    protected TransportRepository transportRepository;

    @Autowired
    protected StoreRepository storeRepository;

    @Autowired
    protected FuelSupplyKeyMapper fuelSupplyKeyMapper;

    @Mapping(target = "fuelIds", expression =
            "java(station.getFuelSupplies().stream().map(x -> x.getId().getFuelId()).toList())")
    @Mapping(target = "transportIds", expression =
            "java(station.getTransports().stream().map(x -> x.getId()).toList())")
    @Mapping(target = "employeeIds", expression =
            "java(station.getEmployees().stream().map(x -> x.getId()).toList())")
    @Mapping(target = "storeId", source = "station.store.id")
    public abstract StationRetrievalDto toStationDto(Station station);

    public abstract List<StationRetrievalDto> toStationDtos(List<Station> stations);
}