package org.alexmiclea.reptopetrol.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.alexmiclea.reptopetrol.model.FuelType;
import org.alexmiclea.reptopetrol.model.composites.FuelSupply;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "fuel")
@Getter
@Setter
public class FuelDto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "fuel")
    Set<FuelSupply> fuelSupplies;

    @ManyToMany
    private Set<ContractDto> contracts;

    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    private FuelType type;
}
