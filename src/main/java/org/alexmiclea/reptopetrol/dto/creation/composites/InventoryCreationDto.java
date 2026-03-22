package org.alexmiclea.reptopetrol.dto.creation.composites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alexmiclea.reptopetrol.dto.keys.InventoryKeyDto;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryCreationDto {

    private InventoryKeyDto id;
    private UUID store;
    private UUID product;
    private Integer quantity;
    private Float price;
    private Instant priceChange;
}
