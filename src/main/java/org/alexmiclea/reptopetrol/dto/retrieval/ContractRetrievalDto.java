package org.alexmiclea.reptopetrol.dto.retrieval;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ContractRetrievalDto {

    private UUID id;
    private List<UUID> transportIds;
    private UUID supplierId;
    private List<UUID> fuelIds;
    private Instant beginDate;
    private Instant endDate;
}
