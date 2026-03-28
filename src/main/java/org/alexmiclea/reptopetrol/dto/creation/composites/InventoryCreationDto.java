package org.alexmiclea.reptopetrol.dto.creation.composites;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alexmiclea.reptopetrol.dto.keys.FuelSupplyKeyDto;
import org.alexmiclea.reptopetrol.dto.keys.InventoryKeyDto;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryCreationDto {

    @NotNull
    private InventoryKeyDto id;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @Positive
    private Float price;

    @NotNull
    private Instant priceChange;
}
