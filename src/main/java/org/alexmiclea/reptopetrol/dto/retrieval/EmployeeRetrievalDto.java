package org.alexmiclea.reptopetrol.dto.retrieval;

import lombok.Data;
import org.alexmiclea.reptopetrol.model.EmployeeRole;

import java.time.Instant;
import java.util.UUID;

@Data
public class EmployeeRetrievalDto {

    private UUID id;
    private UUID stationId;
    private String firstName;
    private String lastName;
    private Instant dateOfHire;
    private String identificationNumber;
    private Integer wage;
    private EmployeeRole role;
}