package org.alexmiclea.reptopetrol.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierCreationDto {

    private UUID id;
    private String name;
    private String address;
    private String homeCountry;
    private Set<ContractCreationDto> contracts;
}