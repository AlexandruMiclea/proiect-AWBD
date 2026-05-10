package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.management.retrieval.TransportRetrievalDto;
import org.alexmiclea.reptopetrol.model.management.Transport;
import org.alexmiclea.reptopetrol.repository.management.ContractRepository;
import org.alexmiclea.reptopetrol.repository.management.StationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TransportRetrievalMapper {

    @Autowired
    protected ContractRepository contractRepository;

    @Autowired
    protected StationRepository stationRepository;

    @Mapping(target = "contractId", source = "transport.contract.id")
    @Mapping(target = "stationIds", expression =
            "java(transport.getStations().stream().map(x -> x.getId()).toList())")
    public abstract TransportRetrievalDto toTransportDto(Transport transport);

    public abstract List<TransportRetrievalDto> toTransportDtos(List<Transport> transports);
}