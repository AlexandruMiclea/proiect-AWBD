package org.alexmiclea.reptopetrol.mapper;

import org.alexmiclea.reptopetrol.dto.TransportDto;
import org.alexmiclea.reptopetrol.model.Transport;
import org.mapstruct.Mapper;
import org.springframework.core.annotation.Order;

import java.util.List;

@Order(1)
@Mapper(componentModel = "spring")
public interface TransportMapper {
    TransportDto toTransportDto(Transport transport);
    Transport toTransport(TransportDto transportDto);

    List<TransportDto> toTransportDtos(List<Transport> transports);
    List<Transport> toTransports(List<TransportDto> transportDtos);
}