package org.alexmiclea.reptopetrol.service.monitoring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.repository.monitoring.CRUDHistoryRepository;
import org.alexmiclea.reptopetrol.repository.monitoring.EventHistoryRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventHistoryService {

    private final EventHistoryRepository eventHistoryRepository;

    // TODO crud and GET with pageable and sortable request
}
