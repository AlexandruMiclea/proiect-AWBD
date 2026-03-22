package org.alexmiclea.reptopetrol.dto.retrieval;

import lombok.Builder;
import lombok.Data;
import org.alexmiclea.reptopetrol.model.FuelType;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class FuelRetrievalDto {

    private UUID id;
    private Set<UUID> fuelSuppliesIds;
    private Set<UUID> contractIds;
    private String name;
    private FuelType type;
}
