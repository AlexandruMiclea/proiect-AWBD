package org.alexmiclea.reptopetrol.service.monitoring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.repository.monitoring.CRUDHistoryRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CRUDHistoryService {

    private final CRUDHistoryRepository crudHistoryRepository;

    // TODO crud and GET with pageable and sortable request


}
