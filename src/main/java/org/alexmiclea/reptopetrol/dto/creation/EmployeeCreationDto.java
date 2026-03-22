package org.alexmiclea.reptopetrol.dto.creation;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alexmiclea.reptopetrol.model.EmployeeRole;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreationDto {

    @Nullable
    private UUID id;

    @Nullable
    private UUID stationId;

    @NotBlank
    @Size(max = 64)
    private String firstName;

    @NotBlank
    @Size(max = 64)
    private String lastName;

    @NotNull
    private Instant dateOfHire;

    @NotBlank
    @Size(max = 64)
    private String identificationNumber;

    @Positive
    private Integer wage;

    @NotNull
    private EmployeeRole role;
}