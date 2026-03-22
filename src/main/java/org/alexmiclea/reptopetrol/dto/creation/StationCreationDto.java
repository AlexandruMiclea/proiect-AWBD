package org.alexmiclea.reptopetrol.dto.creation;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alexmiclea.reptopetrol.dto.keys.FuelSupplyKeyDto;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StationCreationDto {

    @Nullable
    private UUID id;

    @Nullable
    private List<FuelSupplyKeyDto> fuelSuppliesIds;

    @Nullable
    private List<UUID> employeeIds;

    @Nullable
    private UUID storeId;

    @Nullable
    private List<UUID> transportIds;

    @NotBlank
    @Size(max = 64)
    private String name;

    @NotBlank
    @Size(max = 128)
    private String address;

    @NotNull
    @Positive
    private Integer pumpNo;
}
