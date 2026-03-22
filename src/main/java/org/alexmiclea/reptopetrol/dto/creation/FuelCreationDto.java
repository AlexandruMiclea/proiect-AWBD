package org.alexmiclea.reptopetrol.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alexmiclea.reptopetrol.model.FuelType;
import org.alexmiclea.reptopetrol.model.composites.FuelSupply;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuelCreationDto {

    private UUID id;
    private Set<FuelSupply> fuelSupplies;
    private Set<ContractCreationDto> contracts;
    private String name;
    private FuelType type;
}
