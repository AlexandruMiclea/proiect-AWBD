package org.alexmiclea.reptopetrol.dto.creation.composites;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alexmiclea.reptopetrol.model.composites.keys.FuelSupplyKey;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuelSupplyCreationDto {

    @NotNull
    private FuelSupplyKey id;

    @NotNull
    @Positive
    private BigDecimal quantity;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    private Instant priceChange;
}
