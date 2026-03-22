package org.alexmiclea.reptopetrol.dto.creation;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractCreationDto {

    @Nullable
    private UUID id;

    @Nullable
    private List<UUID> transportIds;

    @NotEmpty
    private UUID supplierId;

    @NotEmpty
    private List<UUID> fuelIds;

    @NotNull
    @PastOrPresent
    private Instant beginDate;

    @NotNull
    @FutureOrPresent
    private Instant endDate;
}