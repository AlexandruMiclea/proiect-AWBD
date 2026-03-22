package org.alexmiclea.reptopetrol.dto.retrieval;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class TransportRetrievalDto {

    private UUID id;
    private UUID contractId;
    private List<UUID> stationIds;
    private Instant creationDate;
    private Instant completionDate;
    private String companyName;
}
