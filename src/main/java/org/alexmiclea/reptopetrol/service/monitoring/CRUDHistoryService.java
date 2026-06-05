package org.alexmiclea.reptopetrol.service.monitoring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.model.monitoring.CRUDHistory;
import org.alexmiclea.reptopetrol.repository.monitoring.CRUDHistoryRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
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

    // create the crudHistory object here, leave the relevant data as parameters
    public void add(String operation, String resourceType, String resourceContent) {

        String username;
        String role;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication)) {
            username = authentication.getName();
//            role = authentication.getAuthorities().iterator().next().getAuthority();
            role = "role_user";
        } else {
            username = "unauthenticated_user";
            role = "unauthenticated_role";
        }

        CRUDHistory newHistory = CRUDHistory.builder()
                .id(UUID.randomUUID())
                .username(username)
                .role(role)
                .operation(operation)
                .resourceType(resourceType)
                .resourceContent(resourceContent)
                .timestamp(Instant.now())
                .build();

        crudHistoryRepository.save(newHistory);
    }

    public void delete(UUID id) {
        crudHistoryRepository.deleteById(id);
    }
}
