package org.alexmiclea.reptopetrol.dto.retrieval.composites;

import lombok.Builder;
import lombok.Data;
import org.alexmiclea.reptopetrol.dto.keys.InventoryKeyDto;

import java.time.Instant;

@Data
@Builder
public class InventoryRetrievalDto {

    private InventoryKeyDto id;
    private Integer quantity;
    private Float price;
    private Instant priceChange;
}
