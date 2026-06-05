package org.alexmiclea.reptopetrol.model.monitoring;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Document
@Builder
public class CRUDHistory {

    @Id
    private UUID id;

    private String username;
    private String role;
    private String operation;
    private String resourceType;
    private String resourceContent;
    private Instant timestamp;
}
