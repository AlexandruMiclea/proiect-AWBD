package org.alexmiclea.reptopetrol.integration;

import org.alexmiclea.reptopetrol.repository.monitoring.CRUDHistoryRepository;
import org.alexmiclea.reptopetrol.repository.monitoring.EventHistoryRepository;
import org.alexmiclea.reptopetrol.repository.monitoring.PurchaseRepository;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

// Mongo repos are mocked so the monitoring service beans can be wired without a real MongoDB instance.
// The test-integration profile loads TestDataSourceConfiguration (JPA only) and TestSecurityConfiguration (permit-all).
@SpringBootTest
@ActiveProfiles("test-integration")
@AutoConfigureMockMvc
public abstract class BaseIntegrationTest {

    @MockitoBean
    protected CRUDHistoryRepository crudHistoryRepository;

    @MockitoBean
    protected EventHistoryRepository eventHistoryRepository;

    @MockitoBean
    protected PurchaseRepository purchaseRepository;
}
