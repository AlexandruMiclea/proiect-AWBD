package org.alexmiclea.reptopetrol.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alexmiclea.reptopetrol.model.composites.FuelSupply;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StationCreationDto {
    private UUID id;
    private Set<FuelSupply> fuelSupplies;
    private Set<EmployeeCreationDto> employees;
    private StoreCreationDto store;
    private Set<TransportCreationDto> transports;
    private String name;
    private String address;
    private Integer pumpNo;
}
