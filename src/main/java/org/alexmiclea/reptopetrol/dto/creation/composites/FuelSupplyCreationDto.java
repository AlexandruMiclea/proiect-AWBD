package org.alexmiclea.reptopetrol.dto.creation.composites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alexmiclea.reptopetrol.dto.keys.FuelSupplyKeyDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuelSupplyCreationDto {

    private FuelSupplyKeyDto id;
    private UUID stationId;
    private UUID fuelId;
    private BigDecimal quantity;
    private BigDecimal price;
    private Instant priceChange;
}
