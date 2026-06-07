package org.alexmiclea.reptopetrol.listener;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.model.payment.Purchase;
import org.alexmiclea.reptopetrol.repository.monitoring.PurchaseRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PurchaseListener {

    private final PurchaseRepository purchaseRepository;

    @RabbitListener(queues = "purchaseQueue")
    public void purchase(Purchase purchase) {
        purchaseRepository.save(purchase);
    }
}
