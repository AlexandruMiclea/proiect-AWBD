package org.alexmiclea.reptopetrol.dto.creation;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class SupplierCreationDto {

    private UUID id;
    private String name;
    private String address;
    private String homeCountry;
    private Set<ContractCreationDto> contracts;
}