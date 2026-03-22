package org.alexmiclea.reptopetrol.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportCreationDto {

    private UUID id;
    private ContractCreationDto contract;
    private Set<StationCreationDto> stations;
    private Instant creationDate;
    private Instant completionDate;
    private String companyName;
}
