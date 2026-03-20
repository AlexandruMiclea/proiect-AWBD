package org.alexmiclea.reptopetrol.dto.retrieval;

import lombok.Data;

import java.util.UUID;

@Data
public class StoreRetrievalDto {

    private UUID id;
    private UUID stationId;
}
