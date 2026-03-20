package org.alexmiclea.reptopetrol.dto.creation;

import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
public class TransportCreationDto {

    private UUID id;
    private ContractCreationDto contract;
    private Set<StationCreationDto> stations;
    private Instant creationDate;
    private Instant completionDate;
    private String companyName;
}
