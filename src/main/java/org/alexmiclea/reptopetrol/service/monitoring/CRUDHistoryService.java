package org.alexmiclea.reptopetrol.service.monitoring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.model.monitoring.CRUDHistory;
import org.alexmiclea.reptopetrol.repository.monitoring.CRUDHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CRUDHistoryService {

    private final CRUDHistoryRepository crudHistoryRepository;

    // TODO crud and GET with pageable and sortable request

    public List<CRUDHistory> getAll() {
        return crudHistoryRepository.findAll();
    }

    public void add(CRUDHistory crudHistory) {
        crudHistoryRepository.save(crudHistory);
    }

    public void delete(UUID id) {
        crudHistoryRepository.deleteById(id);
    }
}
