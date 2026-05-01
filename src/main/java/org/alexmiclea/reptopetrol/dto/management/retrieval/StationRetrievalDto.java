package org.alexmiclea.reptopetrol.dto.management.retrieval;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class StationRetrievalDto {
    private UUID id;
    private List<UUID> fuelIds;
    private List<UUID> employeeIds;
    private UUID storeId;
    private List<UUID> transportIds;
    private String name;
    private String address;
    private Integer pumpNo;
}
