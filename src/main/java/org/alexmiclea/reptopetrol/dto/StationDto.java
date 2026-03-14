package org.alexmiclea.reptopetrol.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.alexmiclea.reptopetrol.model.composites.FuelSupply;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "station")
@Getter
@Setter
public class StationDto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "station")
    private Set<FuelSupply> fuelSupplies;

    @OneToMany(mappedBy = "station")
    private Set<EmployeeDto> employees;

    @OneToOne(optional = true)
    private StoreDto store;

    @ManyToMany
    private Set<TransportDto> transports;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @Positive
    @NotNull
    private Integer pumpNo;
}
