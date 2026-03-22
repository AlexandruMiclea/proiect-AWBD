package org.alexmiclea.reptopetrol.dto.retrieval;

import lombok.Builder;
import lombok.Data;
import org.alexmiclea.reptopetrol.model.composites.FuelSupply;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class StationRetrievalDto {
    private UUID id;
    private Set<FuelSupply> fuelSuppliesIds;
    private Set<UUID> employeeIds;
    private UUID storeId;
    private Set<UUID> transportIds;
    private String name;
    private String address;
    private Integer pumpNo;
}
