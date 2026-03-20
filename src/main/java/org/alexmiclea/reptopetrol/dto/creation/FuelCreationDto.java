package org.alexmiclea.reptopetrol.dto.creation;

import lombok.Data;
import org.alexmiclea.reptopetrol.model.FuelType;
import org.alexmiclea.reptopetrol.model.composites.FuelSupply;

import java.util.Set;
import java.util.UUID;

@Data
public class FuelCreationDto {

    private UUID id;
    private Set<FuelSupply> fuelSupplies;
    private Set<ContractCreationDto> contracts;
    private String name;
    private FuelType type;
}
