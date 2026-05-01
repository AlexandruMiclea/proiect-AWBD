package org.alexmiclea.reptopetrol.model.monitoring;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Document
public class CRUDHistory {

    private UUID id;
    private String username;
    private String role;
    private String operation;
    private String resourceType;
    private String resourceId;
    private Instant timestamp;
    private String previousState;
}
