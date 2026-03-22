package org.alexmiclea.reptopetrol.dto.creation;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
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
public class TransportCreationDto {

    @Nullable
    private UUID id;

    @NotNull
    private UUID contractId;

    @NotEmpty
    private List<UUID> stationIds;

    @PastOrPresent
    @NotEmpty
    private Instant creationDate;

    @FutureOrPresent
    @Nullable
    private Instant completionDate;

    @NotBlank
    @Size(max = 128)
    private String companyName;
}
