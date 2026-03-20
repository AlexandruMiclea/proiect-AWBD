package org.alexmiclea.reptopetrol.dto.retrieval;

import lombok.Data;
import org.alexmiclea.reptopetrol.model.Fuel;
import org.alexmiclea.reptopetrol.model.Station;
import org.alexmiclea.reptopetrol.model.composites.keys.FuelSupplyKey;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class FuelSupplyRetrievalDto {

    private FuelSupplyKey id;
    private Station station;
    private Fuel fuel;
    private BigDecimal quantity;
    private BigDecimal price;
    private Instant priceChange;
}
