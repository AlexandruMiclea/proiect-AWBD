package org.alexmiclea.reptopetrol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "supplier")
@Getter
@Setter
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "supplier")
    private List<Contract> contracts;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String homeCountry;

}
