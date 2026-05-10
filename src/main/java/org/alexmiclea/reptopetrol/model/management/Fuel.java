package org.alexmiclea.reptopetrol.model.management;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.alexmiclea.reptopetrol.model.management.composites.FuelSupply;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "fuel")
@Getter
@Setter
public class Fuel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "fuel")
    private List<FuelSupply> fuelSupplies;

    @ManyToMany(mappedBy = "fuels")
    private List<Contract> contracts;

    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    private FuelType type;
}
