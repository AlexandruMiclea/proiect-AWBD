package org.alexmiclea.reptopetrol.dto.retrieval;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class SupplierRetrievalDto {

    private UUID id;
    private String name;
    private String address;
    private String homeCountry;
    private Set<UUID> contractIds;
}