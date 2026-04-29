package org.alexmiclea.reptopetrol.model.monitoring;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class EventHistory {

    private UUID id;
    private String eventType;
    private String queueName;
    private Instant timestamp;
    private String eventContent;
}