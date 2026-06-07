package org.alexmiclea.reptopetrol.controller.monitoring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.model.monitoring.CRUDHistory;
import org.alexmiclea.reptopetrol.model.monitoring.EventHistory;
import org.alexmiclea.reptopetrol.model.payment.Purchase;
import org.alexmiclea.reptopetrol.service.monitoring.CRUDHistoryService;
import org.alexmiclea.reptopetrol.service.monitoring.EventHistoryService;
import org.alexmiclea.reptopetrol.service.monitoring.PurchaseService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.UUID;

@Controller
@RequestMapping("/api/monitoring")
@Slf4j
@RequiredArgsConstructor
public class MonitoringController {

    private final PurchaseService purchaseService;
    private final EventHistoryService eventHistoryService;
    private final CRUDHistoryService crudHistoryService;

    @GetMapping("/crud")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getCruds(Model model, Pageable pageable) {
        log.debug("GET /crud called");

        // add call to history service
        crudHistoryService.add("GET /crud", CRUDHistory.class.getName(), pageable.toString());

        model.addAttribute("crudList", crudHistoryService.getAll(pageable));

        String sortField = "timestamp";
        String sortDir = "desc";
        if (pageable.getSort().isSorted()) {
            Sort.Order order = pageable.getSort().iterator().next();
            sortField = order.getProperty();
            sortDir = order.getDirection().name().toLowerCase();
        }
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);

        return "monitoring/crud/index";
    }

    @GetMapping("/purchase")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getPurchases(Model model, Pageable pageable) {
        log.debug("GET /purchase called");

        // add call to history service
        crudHistoryService.add("GET /purchase", Purchase.class.getName(), pageable.toString());

        model.addAttribute("purchaseList", purchaseService.getAll(pageable));

        String sortField = "paymentTime";
        String sortDir = "desc";
        if (pageable.getSort().isSorted()) {
            Sort.Order order = pageable.getSort().iterator().next();
            sortField = order.getProperty();
            sortDir = order.getDirection().name().toLowerCase();
        }
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);

        return "monitoring/purchases/index";
    }

    @GetMapping("/event")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getEvents(Model model, Pageable pageable) {
        log.debug("GET /event called");

        // add call to history service
        crudHistoryService.add("GET /event", EventHistory.class.getName(), pageable.toString());

        model.addAttribute("eventList", eventHistoryService.getAll(pageable));

        String sortField = "timestamp";
        String sortDir = "desc";
        if (pageable.getSort().isSorted()) {
            Sort.Order order = pageable.getSort().iterator().next();
            sortField = order.getProperty();
            sortDir = order.getDirection().name().toLowerCase();
        }
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);

        return "monitoring/events/index";
    }

    @DeleteMapping("/crud/delete/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCrud(@PathVariable UUID uuid) {
        log.debug("DELETE /crud/delete called for UUID {}", uuid);

        // add call to history service
        crudHistoryService.add("DELETE /crud/delete", CRUDHistory.class.getName(), uuid.toString());

        crudHistoryService.delete(uuid);

        return "redirect:/api/monitoring/crud";
    }

    @DeleteMapping("/purchase/delete/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deletePurchase(@PathVariable UUID uuid) {
        log.debug("DELETE /purchase/delete called for UUID {}", uuid);

        // add call to history service
        crudHistoryService.add("DELETE /purchase/delete", Purchase.class.getName(), uuid.toString());

        purchaseService.delete(uuid);

        return "redirect:/api/monitoring/purchase";
    }

    @DeleteMapping("/event/delete/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteEvent(@PathVariable UUID uuid) {
        log.debug("DELETE /event/delete called for UUID {}", uuid);

        // add call to history service
        crudHistoryService.add("DELETE /event/delete", EventHistory.class.getName(), uuid.toString());

        eventHistoryService.delete(uuid);

        return "redirect:/api/monitoring/event";
    }
}
