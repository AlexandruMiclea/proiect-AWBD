package org.alexmiclea.reptopetrol.dto.retrieval;

import lombok.Builder;
import lombok.Data;
import org.alexmiclea.reptopetrol.dto.keys.InventoryKeyDto;
import org.alexmiclea.reptopetrol.model.Product;
import org.alexmiclea.reptopetrol.model.Store;

import java.time.Instant;

@Data
@Builder
public class InventoryRetrievalDto {

    private InventoryKeyDto id;
    private Store store;
    private Product product;
    private Integer quantity;
    private Float price;
    private Instant priceChange;
}
