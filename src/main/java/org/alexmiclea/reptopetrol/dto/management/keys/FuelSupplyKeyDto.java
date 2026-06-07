package org.alexmiclea.reptopetrol.dto.management.keys;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FuelSupplyKeyDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    UUID stationId;

    @NotNull
    UUID fuelId;
}
