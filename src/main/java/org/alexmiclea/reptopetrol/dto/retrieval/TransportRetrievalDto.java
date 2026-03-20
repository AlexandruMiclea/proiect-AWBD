package org.alexmiclea.reptopetrol.dto.retrieval;

import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
public class TransportRetrievalDto {

    private UUID id;
    private UUID contractId;
    private Set<UUID> stationIds;
    private Instant creationDate;
    private Instant completionDate;
    private String companyName;
}
