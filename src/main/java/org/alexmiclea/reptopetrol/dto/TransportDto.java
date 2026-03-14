package org.alexmiclea.reptopetrol.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
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
public class TransportDto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="contract_id")
    private ContractDto contract;

    @ManyToMany
    private Set<StationDto> stations;

    @CreatedDate
    private Instant creationDate;

    @FutureOrPresent
    private Instant completionDate;

    @NotBlank
    private String companyName;
}
