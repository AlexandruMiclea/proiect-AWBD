package org.alexmiclea.reptopetrol.listener;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.model.monitoring.EventHistory;
import org.alexmiclea.reptopetrol.model.monitoring.PriceChange;
import org.alexmiclea.reptopetrol.service.monitoring.EventHistoryService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceChangeListener {

    private final EventHistoryService eventHistoryService;

    @RabbitListener(queues = "priceChangeQueue")
    public void purchase(PriceChange priceChange) {


        eventHistoryService.add();
    }
}