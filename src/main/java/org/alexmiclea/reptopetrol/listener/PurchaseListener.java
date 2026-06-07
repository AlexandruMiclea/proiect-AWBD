package org.alexmiclea.reptopetrol.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.model.payment.Purchase;
import org.alexmiclea.reptopetrol.service.monitoring.EventHistoryService;
import org.alexmiclea.reptopetrol.service.monitoring.PurchaseService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PurchaseListener {

    private final PurchaseService purchaseService;
    private final EventHistoryService eventHistoryService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "purchaseQueue")
    public void purchaseListener(Message purchaseMessage) {
        try {
            Purchase purchase = objectMapper.readValue(purchaseMessage.getBody(), Purchase.class);

            if (purchase.getId() == null) {
                purchase.setId(UUID.randomUUID());
            }

            log.debug("Message received in PurchaseListener: {}", purchase);

            eventHistoryService.add("Purchase", "purchaseQueue", purchase.toString());

            purchaseService.add(purchase);
        } catch (Exception e) {
            log.error("Error while processing purchase message: {}", e.getMessage());
            log.debug("Error while processing purchase message", e);
        }
    }
}
