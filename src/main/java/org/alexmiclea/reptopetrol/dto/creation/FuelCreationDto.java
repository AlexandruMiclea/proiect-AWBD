package org.alexmiclea.reptopetrol.dto.creation;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alexmiclea.reptopetrol.dto.keys.FuelSupplyKeyDto;
import org.alexmiclea.reptopetrol.model.FuelType;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuelCreationDto {

    @Nullable
    private UUID id;

    @Nullable
    private List<FuelSupplyKeyDto> fuelSuppliesIds;

    @Nullable
    private List<UUID> contractIds;

    @NotBlank
    @Size(max = 64)
    private String name;

    @NotNull
    private FuelType type;
}