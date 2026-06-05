package org.alexmiclea.reptopetrol.service.monitoring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.model.monitoring.CRUDHistory;
import org.alexmiclea.reptopetrol.model.monitoring.EventHistory;
import org.alexmiclea.reptopetrol.model.payment.Purchase;
import org.alexmiclea.reptopetrol.repository.monitoring.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    // TODO crud and GET with pageable and sortable request

    public List<Purchase> getAll() {
        return purchaseRepository.findAll();
    }

    public void add(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    public void delete(UUID id) {
        purchaseRepository.deleteById(id);
    }
}
 