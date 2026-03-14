package org.alexmiclea.reptopetrol.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "supplier")
@Getter
@Setter
public class SupplierDto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "supplier")
    private Set<ContractDto> contracts;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String homeCountry;

}
