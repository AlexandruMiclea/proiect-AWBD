package org.alexmiclea.reptopetrol.mapper.creation;

import org.alexmiclea.reptopetrol.dto.creation.TransportCreationDto;
import org.alexmiclea.reptopetrol.model.Transport;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransportCreationMapper {
    TransportCreationDto toTransportDto(Transport transport);
    Transport toTransport(TransportCreationDto transportDto);

    List<TransportCreationDto> toTransportDtos(List<Transport> transports);
    List<Transport> toTransports(List<TransportCreationDto> transportDtos);
}