package org.alexmiclea.reptopetrol.dto.management.retrieval;

import lombok.Builder;
import lombok.Data;
import org.alexmiclea.reptopetrol.model.management.FuelType;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class FuelRetrievalDto {

    private UUID id;
    private List<UUID> stationIds;
    private List<UUID> contractIds;
    private String name;
    private FuelType type;
}
