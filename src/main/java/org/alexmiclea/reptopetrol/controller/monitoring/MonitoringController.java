package org.alexmiclea.reptopetrol.controller.monitoring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.service.monitoring.CRUDHistoryService;
import org.alexmiclea.reptopetrol.service.monitoring.EventHistoryService;
import org.alexmiclea.reptopetrol.service.monitoring.PurchaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/api/monitoring")
@Slf4j
@RequiredArgsConstructor
public class MonitoringController {

    private final PurchaseService purchaseService;
    private final EventHistoryService eventHistoryService;

    private final CRUDHistoryService crudHistoryService;

    @GetMapping("/crud")
    //@Secured({"ROLE_ADMIN"})
    public String getCruds(Model model) {
        log.debug("GET /crud called");

//        model.addAttribute("crudList", crudHistoryService.getAll());

        return "monitoring/crud/index";
    }
    @GetMapping("/purchase")
    //@Secured({"ROLE_ADMIN"})
    public String getPurchases(Model model) {
        log.debug("GET /all called");

//        model.addAttribute("purchaseList", purchaseService.getAll());

        return "monitoring/purchases/index";
    }

    @GetMapping("/event")
    //@Secured({"ROLE_ADMIN"})
    public String getEvents(Model model) {
        log.debug("GET /event called");

//        model.addAttribute("eventList", eventHistoryService.getAll());

        return "monitoring/events/index";
    }
}
