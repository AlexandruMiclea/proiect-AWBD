package org.alexmiclea.reptopetrol.model.management.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@ToString
public class FuelSupplyKey implements Serializable {
    @Column(name = "station_id")
    UUID stationId;

    @Column(name = "fuel_id")
    UUID fuelId;
}
