package org.alexmiclea.reptopetrol.dto.creation;

import lombok.Data;
import org.alexmiclea.reptopetrol.model.Product;
import org.alexmiclea.reptopetrol.model.Store;
import org.alexmiclea.reptopetrol.model.composites.keys.InventoryKey;

import java.time.Instant;

@Data
public class InventoryCreationDto {

    private InventoryKey id;
    private Store store;
    private Product product;
    private Integer quantity;
    private Float price;
    private Instant priceChange;
}
