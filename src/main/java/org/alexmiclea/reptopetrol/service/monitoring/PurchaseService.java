package org.alexmiclea.reptopetrol.service.monitoring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.model.payment.Purchase;
import org.alexmiclea.reptopetrol.repository.monitoring.PurchaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public Page<Purchase> getAll(Pageable pageable) {
        return purchaseRepository.findAll(pageable);
    }

    public void add(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    public void delete(UUID id) {
        purchaseRepository.deleteById(id);
    }
}
 