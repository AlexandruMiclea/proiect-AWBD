package org.alexmiclea.reptopetrol.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class EmployeeDto {

    private enum EmployeeRole {
        CASHIER, ATTENDANT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private StationDto station;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotEmpty
    private Instant dateOfHire;

    @NotBlank
    private String identificationNumber;

    @Positive
    private Integer wage;

    @Enumerated(EnumType.STRING)
    private EmployeeRole role;
}