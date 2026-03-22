package org.alexmiclea.reptopetrol.dto.retrieval;

import lombok.Builder;
import lombok.Data;
import org.alexmiclea.reptopetrol.dto.keys.FuelSupplyKeyDto;
import org.alexmiclea.reptopetrol.model.FuelType;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class FuelRetrievalDto {

    private UUID id;
    private List<FuelSupplyKeyDto> fuelSuppliesIds;
    private List<UUID> contractIds;
    private String name;
    private FuelType type;
}
