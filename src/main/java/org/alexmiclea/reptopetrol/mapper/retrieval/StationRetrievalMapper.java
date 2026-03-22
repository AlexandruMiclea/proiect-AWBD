package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.StationRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.keys.FuelSupplyKeyMapper;
import org.alexmiclea.reptopetrol.model.Station;
import org.alexmiclea.reptopetrol.repository.EmployeeRepository;
import org.alexmiclea.reptopetrol.repository.FuelSupplyRepository;
import org.alexmiclea.reptopetrol.repository.StoreRepository;
import org.alexmiclea.reptopetrol.repository.TransportRepository;
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

    @Mapping(target = "fuelSuppliesIds", expression =
            "java(fuelSupplyKeyMapper.toFuelSupplyKeyDtos(" +
                    "station.getFuelSupplies().stream().map(x -> x.getId()).toList())" +
            ")")
    @Mapping(target = "transportIds", expression =
            "java(station.getTransports().stream().map(x -> x.getId()).toList())")
    @Mapping(target = "employeeIds", expression =
            "java(station.getEmployees().stream().map(x -> x.getId()).toList())")
    @Mapping(target = "storeId", source = "station.store.id")
    public abstract StationRetrievalDto toStationDto(Station station);

    @Mapping(target = "fuelSupplies", expression =
            "java(fuelSupplyRepository.findAllById(fuelSupplyKeyMapper.toFuelSupplyKeys(stationDto.getFuelSuppliesIds())))")
    @Mapping(target = "transports", expression =
            "java(transportRepository.findAllById(stationDto.getTransportIds()))")
    @Mapping(target = "employees", expression =
            "java(employeeRepository.findAllById(stationDto.getEmployeeIds()))")
    @Mapping(target = "store", expression =
            "java(storeRepository.findById(stationDto.getStoreId()).orElseThrow())")
    @Mapping(target = "id", ignore = true)
    public abstract Station toStation(StationRetrievalDto stationDto);

    public abstract List<StationRetrievalDto> toStationDtos(List<Station> stations);
    public abstract List<Station> toStations(List<StationRetrievalDto> stationDtos);
}