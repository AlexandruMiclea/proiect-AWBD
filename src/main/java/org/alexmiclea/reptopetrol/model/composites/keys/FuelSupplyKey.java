package org.alexmiclea.reptopetrol.model.composites.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class FuelSupplyKey implements Serializable {
    @Column(name = "station_id")
    UUID stationId;

    @Column(name = "fuel_id")
    UUID fuelId;
}
