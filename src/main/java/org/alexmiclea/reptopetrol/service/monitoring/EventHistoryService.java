package org.alexmiclea.reptopetrol.service.monitoring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.model.monitoring.CRUDHistory;
import org.alexmiclea.reptopetrol.model.monitoring.EventHistory;
import org.alexmiclea.reptopetrol.repository.monitoring.CRUDHistoryRepository;
import org.alexmiclea.reptopetrol.repository.monitoring.EventHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventHistoryService {

    private final EventHistoryRepository eventHistoryRepository;

    // TODO crud and GET with pageable and sortable request

    public List<EventHistory> getAll() {
        return eventHistoryRepository.findAll();
    }

    public void add(EventHistory eventHistory) {
        eventHistoryRepository.save(eventHistory);
    }

    public void delete(UUID id) {
        eventHistoryRepository.deleteById(id);
    }
}
