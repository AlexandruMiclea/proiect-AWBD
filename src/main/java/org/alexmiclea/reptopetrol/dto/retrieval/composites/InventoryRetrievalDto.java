package org.alexmiclea.reptopetrol.dto.retrieval.composites;

import lombok.Builder;
import lombok.Data;
import org.alexmiclea.reptopetrol.dto.keys.InventoryKeyDto;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class InventoryRetrievalDto {

    private InventoryKeyDto id;
    private UUID storeId;
    private UUID productId;
    private Integer quantity;
    private Float price;
    private Instant priceChange;
}
