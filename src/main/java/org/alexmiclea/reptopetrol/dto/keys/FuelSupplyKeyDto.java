package org.alexmiclea.reptopetrol.dto.keys;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class FuelSupplyKeyDto implements Serializable {

    UUID stationId;
    UUID fuelId;
}
