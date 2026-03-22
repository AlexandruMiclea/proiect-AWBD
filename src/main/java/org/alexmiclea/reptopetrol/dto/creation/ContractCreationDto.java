package org.alexmiclea.reptopetrol.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractCreationDto {

    private List<UUID> transportIds;
    private UUID supplierId;
    private List<UUID> fuelIds;
    private Instant beginDate;
    private Instant endDate;
}