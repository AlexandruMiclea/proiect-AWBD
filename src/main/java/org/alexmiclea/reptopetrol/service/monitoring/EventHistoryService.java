package org.alexmiclea.reptopetrol.service.monitoring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.model.monitoring.CRUDHistory;
import org.alexmiclea.reptopetrol.model.monitoring.EventHistory;
import org.alexmiclea.reptopetrol.repository.monitoring.CRUDHistoryRepository;
import org.alexmiclea.reptopetrol.repository.monitoring.EventHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventHistoryService {

    private final EventHistoryRepository eventHistoryRepository;

    public List<EventHistory> getAll() {
        return eventHistoryRepository.findAll();
    }

    public void add(String eventType, String queueName, String eventContent) {

        EventHistory eventHistory = EventHistory.builder()
                .id(UUID.randomUUID())
                .eventType(eventType)
                .queueName(queueName)
                .timestamp(Instant.now())
                .eventContent(eventContent)
                .build();

        eventHistoryRepository.save(eventHistory);
    }

    public void delete(UUID id) {
        eventHistoryRepository.deleteById(id);
    }
}
