package org.alexmiclea.reptopetrol.model;

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
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "station")
    private Set<FuelSupply> fuelSupplies;

    @OneToMany(mappedBy = "station")
    private Set<Employee> employees;

    @OneToOne(optional = true)
    private Store store;

    @ManyToMany
    private Set<Transport> transports;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @Positive
    @NotNull
    private Integer pumpNo;
}
