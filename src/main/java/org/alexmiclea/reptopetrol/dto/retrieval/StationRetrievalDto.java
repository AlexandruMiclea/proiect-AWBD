package org.alexmiclea.reptopetrol.dto.retrieval;

import lombok.Builder;
import lombok.Data;
import org.alexmiclea.reptopetrol.dto.keys.FuelSupplyKeyDto;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class StationRetrievalDto {
    private UUID id;
    private List<FuelSupplyKeyDto> fuelSuppliesIds;
    private List<UUID> employeeIds;
    private UUID storeId;
    private List<UUID> transportIds;
    private String name;
    private String address;
    private Integer pumpNo;
}
