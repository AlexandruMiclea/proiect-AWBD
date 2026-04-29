package org.alexmiclea.reptopetrol.model.monitoring;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
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
