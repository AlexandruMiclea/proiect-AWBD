package org.alexmiclea.reptopetrol.repository.monitoring;

import org.alexmiclea.reptopetrol.model.monitoring.CRUDHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CRUDHistoryRepository extends MongoRepository<CRUDHistory, UUID> { }
