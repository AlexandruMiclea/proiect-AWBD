package org.alexmiclea.reptopetrol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "employee")
@Getter
@Setter
@Configuration
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private Instant dateOfHire;

    @NotBlank
    private String identificationNumber;

    @Positive
    private Integer wage;

    @Enumerated(EnumType.STRING)
    private EmployeeRole role;
}