package org.alexmiclea.reptopetrol.dto.retrieval.composites;

import lombok.Builder;
import lombok.Data;
import org.alexmiclea.reptopetrol.dto.keys.FuelSupplyKeyDto;
import org.alexmiclea.reptopetrol.model.Fuel;
import org.alexmiclea.reptopetrol.model.Station;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class FuelSupplyRetrievalDto {

    private FuelSupplyKeyDto id;
    private Station station;
    private Fuel fuel;
    private BigDecimal quantity;
    private BigDecimal price;
    private Instant priceChange;
}
