package org.alexmiclea.reptopetrol.controller.monitoring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.model.monitoring.CRUDHistory;
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

    // TODO refactor the get mapping above to get some sort of pageable & sortable request and alter the frontend
    // to ask for the relevant data

//    @GetMapping("/purchase")
//    //@PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String getPurchases(Model model) {
//        log.debug("GET /all called");
//
////        model.addAttribute("purchaseList", purchaseService.getAll());
//
//        return "monitoring/purchases/index";
//    }
//
//    @GetMapping("/event")
//    //@PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String getEvents(Model model) {
//        log.debug("GET /event called");
//
////        model.addAttribute("eventList", eventHistoryService.getAll());
//
//        return "monitoring/events/index";
//    }

    @DeleteMapping("/crud/delete/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCrud(@PathVariable UUID uuid) {
        log.debug("DELETE /crud/delete called for UUID {}", uuid);

        // add call to history service
        crudHistoryService.add("DELETE /crud/delete", CRUDHistory.class.getName(), uuid.toString());

        crudHistoryService.delete(uuid);

        return "redirect:/api/monitoring/crud";
    }

//    @DeleteMapping("/purchase/delete/{uuid}")
//    //@PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String deletePurchase(@PathVariable UUID uuid) {
//        log.debug("DELETE /delete called for UUID {}", uuid);
//
//        // add call to history service
//        crudHistoryService.add("DELETE /delete", ContractRetrievalDto.class.getName(), uuid.toString());
//
//        Optional<UUID> response = contractService.deleteContract(uuid);
//
//        log.debug("Database response for DELETE: {}", response);
//
//        return "redirect:/api/contract/all";
//    }
//
//    @DeleteMapping("/event/delete/{uuid}")
//    //@PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String deleteEvent(@PathVariable UUID uuid) {
//        log.debug("DELETE /delete called for UUID {}", uuid);
//
//        // add call to history service
//        crudHistoryService.add("DELETE /delete", ContractRetrievalDto.class.getName(), uuid.toString());
//
//        Optional<UUID> response = contractService.deleteContract(uuid);
//
//        log.debug("Database response for DELETE: {}", response);
//
//        return "redirect:/api/contract/all";
//    }
}
