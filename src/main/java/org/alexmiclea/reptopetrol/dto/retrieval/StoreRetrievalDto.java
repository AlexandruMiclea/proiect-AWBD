package org.alexmiclea.reptopetrol.dto.retrieval;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class StoreRetrievalDto {

    private UUID id;
    private UUID stationId;
}
