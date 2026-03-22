package org.alexmiclea.reptopetrol.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractCreationDto {

//    private UUID id;
//    private Set<TransportCreationDto> transports;
    private SupplierCreationDto supplier;
    private Set<FuelCreationDto> fuels;
    private Instant beginDate;
    private Instant endDate;
}
