package org.alexmiclea.reptopetrol.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.model.monitoring.PriceChange;
import org.alexmiclea.reptopetrol.model.payment.Purchase;
import org.alexmiclea.reptopetrol.service.management.FuelSupplyService;
import org.alexmiclea.reptopetrol.service.management.InventoryService;
import org.alexmiclea.reptopetrol.service.monitoring.EventHistoryService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class PriceChangeListener {

    private final EventHistoryService eventHistoryService;
    private final FuelSupplyService fuelSupplyService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "priceChangeQueue")
    public void priceChangeListener(Message priceChangeMessage) {
        try {
            PriceChange priceChange = objectMapper.readValue(priceChangeMessage.getBody(), PriceChange.class);

            log.debug("Message received in PriceChangeListener: {}", priceChange);

            eventHistoryService.add("Price Change", "priceChangeQueue", priceChange.toString());

            fuelSupplyService.updateFuelSupplyPrice(priceChange.getPrice(), priceChange.getKey());
        } catch (Exception e) {
            log.error("Error while processing purchase message: {}", e.getMessage());
            log.debug("Error while processing purchase message", e);
        }
    }
}