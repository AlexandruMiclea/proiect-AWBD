package org.alexmiclea.reptopetrol.dto.creation;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreCreationDto {

    @Nullable
    private UUID id;

    @NotNull
    private UUID stationId;
}
