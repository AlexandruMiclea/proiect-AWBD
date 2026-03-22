package org.alexmiclea.reptopetrol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.List;
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
    private List<Station> stations;

    @CreatedDate
    private Instant creationDate;

    @FutureOrPresent
    private Instant completionDate;

    @NotBlank
    private String companyName;
}
