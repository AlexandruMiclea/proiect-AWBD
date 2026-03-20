package org.alexmiclea.reptopetrol.dto.retrieval;

import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
public class ContractRetrievalDto {

    private UUID id;
    private Set<UUID> transportIds;
    private UUID supplierId;
    private Set<UUID> fuelIds;
    private Instant beginDate;
    private Instant endDate;
}
