package org.alexmiclea.reptopetrol.dto.management.retrieval.composites;

import lombok.Builder;
import lombok.Data;
import org.alexmiclea.reptopetrol.dto.management.keys.InventoryKeyDto;

import java.time.Instant;

@Data
@Builder
public class InventoryRetrievalDto {

    private InventoryKeyDto id;
    private Integer quantity;
    private Float price;
    private Instant priceChange;
}
