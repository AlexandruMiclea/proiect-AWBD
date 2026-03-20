package org.alexmiclea.reptopetrol.dto.retrieval;

import lombok.Data;
import org.alexmiclea.reptopetrol.model.Product;
import org.alexmiclea.reptopetrol.model.Store;
import org.alexmiclea.reptopetrol.model.composites.keys.InventoryKey;

import java.time.Instant;

@Data
public class InventoryRetrievalDto {

    private InventoryKey id;
    private Store store;
    private Product product;
    private Integer quantity;
    private Float price;
    private Instant priceChange;
}
