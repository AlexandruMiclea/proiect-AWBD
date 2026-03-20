package org.alexmiclea.reptopetrol.dto.creation;

import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
public class ContractCreationDto {

//    private UUID id;
//    private Set<TransportCreationDto> transports;
    private SupplierCreationDto supplier;
    private Set<FuelCreationDto> fuels;
    private Instant beginDate;
    private Instant endDate;
}
