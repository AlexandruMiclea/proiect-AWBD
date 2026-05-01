package org.alexmiclea.reptopetrol.repository.monitoring;

import org.alexmiclea.reptopetrol.model.monitoring.EventHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface EventHistoryRepository extends MongoRepository<EventHistory, UUID> { }