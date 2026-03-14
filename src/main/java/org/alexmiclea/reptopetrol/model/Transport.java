package org.alexmiclea.reptopetrol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "transport")
@Getter
@Setter
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="contract_id")
    private Contract contract;

    @ManyToMany
    private Set<Station> stations;

    @CreatedDate
    private Instant creationDate;

    @FutureOrPresent
    private Instant completionDate;

    @NotBlank
    private String companyName;
}
