package org.alexmiclea.reptopetrol.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportCreationDto {

    private UUID id;
    private UUID contractId;
    private List<UUID> stationIds;
    private Instant creationDate;
    private Instant completionDate;
    private String companyName;
}
