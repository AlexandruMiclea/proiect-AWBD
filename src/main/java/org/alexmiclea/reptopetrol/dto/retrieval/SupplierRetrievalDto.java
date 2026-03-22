package org.alexmiclea.reptopetrol.dto.retrieval;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class SupplierRetrievalDto {

    private UUID id;
    private String name;
    private String address;
    private String homeCountry;
    private List<UUID> contractIds;
}