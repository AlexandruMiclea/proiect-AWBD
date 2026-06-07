package org.alexmiclea.reptopetrol.model.monitoring;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class EventHistory {

    @Id
    private UUID id;


    private String eventType;
    private String queueName;
    private Instant timestamp;
    private String eventContent;
}