package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.TransportRetrievalDto;
import org.alexmiclea.reptopetrol.model.Transport;
import org.alexmiclea.reptopetrol.repository.ContractRepository;
import org.alexmiclea.reptopetrol.repository.StationRepository;
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

    @Mapping(target = "contract", expression =
            "java(contractRepository.findById(transportDto.getContractId()).orElseThrow())")
    @Mapping(target = "stations", expression =
            "java(stationRepository.findAllById(transportDto.getStationIds()))")
    @Mapping(target = "id", ignore = true)
    public abstract Transport toTransport(TransportRetrievalDto transportDto);

    public abstract List<TransportRetrievalDto> toTransportDtos(List<Transport> transports);
    public abstract List<Transport> toTransports(List<TransportRetrievalDto> transportDtos);
}