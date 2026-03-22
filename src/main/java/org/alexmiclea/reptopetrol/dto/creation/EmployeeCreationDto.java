package org.alexmiclea.reptopetrol.dto.creation;

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

    private UUID id;
    private StationCreationDto station;
    private String firstName;
    private String lastName;
    private Instant dateOfHire;
    private String identificationNumber;
    private Integer wage;
    private EmployeeRole role;
}