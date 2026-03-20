package org.alexmiclea.reptopetrol.mapper.retrieval;

import org.alexmiclea.reptopetrol.dto.retrieval.TransportRetrievalDto;
import org.alexmiclea.reptopetrol.model.Transport;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransportRetrievalMapper {
    TransportRetrievalDto toTransportDto(Transport transport);
    Transport toTransport(TransportRetrievalDto transportDto);

    List<TransportRetrievalDto> toTransportDtos(List<Transport> transports);
    List<Transport> toTransports(List<TransportRetrievalDto> transportDtos);
}