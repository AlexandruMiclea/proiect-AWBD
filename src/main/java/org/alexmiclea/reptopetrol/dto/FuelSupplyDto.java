package org.alexmiclea.reptopetrol.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.alexmiclea.reptopetrol.model.Fuel;
import org.alexmiclea.reptopetrol.model.Station;
import org.alexmiclea.reptopetrol.model.composites.keys.FuelSupplyKey;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "fuel_supply")
public class FuelSupplyDto {
    @EmbeddedId
    private FuelSupplyKey id;

    @ManyToOne
    @MapsId("stationId")
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne
    @MapsId("fuelId")
    @JoinColumn(name = "fuel_id")
    private Fuel fuel;

    @NotNull
    @Positive
    private BigDecimal quantity;

    @NotNull
    @Positive
    private BigDecimal price;

    @LastModifiedDate
    private Instant priceChange;
}
