package org.alexmiclea.reptopetrol.repository.monitoring;

import org.alexmiclea.reptopetrol.model.monitoring.CRUDHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CRUDHistoryRepository extends MongoRepository<CRUDHistory, UUID>, PagingAndSortingRepository<CRUDHistory, UUID> { }
