package org.alexmiclea.reptopetrol.repository.monitoring;

import org.alexmiclea.reptopetrol.model.monitoring.EventHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventHistoryRepository extends MongoRepository<EventHistory, UUID> { }