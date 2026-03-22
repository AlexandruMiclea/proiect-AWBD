package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.TransportCreationDto;
import org.alexmiclea.reptopetrol.model.Transport;
import org.alexmiclea.reptopetrol.repository.ContractRepository;
import org.alexmiclea.reptopetrol.repository.StationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TransportCreationMapper {

    @Autowired
    protected ContractRepository contractRepository;

    @Autowired
    protected StationRepository stationRepository;

    @Mapping(target = "contractId", source = "transport.contract.id")
    @Mapping(target = "stationIds", expression =
            "java(transport.getStations().stream().map(x -> x.getId()).toList())")
    public abstract TransportCreationDto toTransportDto(Transport transport);

    @Mapping(target = "contract", expression =
            "java(contractRepository.findById(transportDto.getContractId()).orElseThrow())")
    @Mapping(target = "stations", expression =
            "java(stationRepository.findAllById(transportDto.getStationIds()))")
    @Mapping(target = "id", ignore = true)
    public abstract Transport toTransport(TransportCreationDto transportDto);

    public abstract List<TransportCreationDto> toTransportDtos(List<Transport> transports);
    public abstract List<Transport> toTransports(List<TransportCreationDto> transportDtos);
}