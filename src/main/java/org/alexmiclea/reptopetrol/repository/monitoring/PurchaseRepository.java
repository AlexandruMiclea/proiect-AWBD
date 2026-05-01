package org.alexmiclea.reptopetrol.repository.monitoring;

import org.alexmiclea.reptopetrol.model.payment.Purchase;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PurchaseRepository extends MongoRepository<Purchase, UUID> {}