package org.alexmiclea.reptopetrol.repository.monitoring;

import org.alexmiclea.reptopetrol.model.payment.Purchase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PurchaseRepository extends MongoRepository<Purchase, UUID> {}