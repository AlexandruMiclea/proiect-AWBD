package org.alexmiclea.reptopetrol.controller.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.management.creation.TransportCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.TransportRetrievalDto;
import org.alexmiclea.reptopetrol.service.management.ContractService;
import org.alexmiclea.reptopetrol.service.management.StationService;
import org.alexmiclea.reptopetrol.service.management.TransportService;
import org.alexmiclea.reptopetrol.service.monitoring.CRUDHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/api/transport")
@RequiredArgsConstructor
@Slf4j
public class TransportController {

    private final TransportService transportService;
    private final ContractService contractService;
    private final StationService stationService;

    private final CRUDHistoryService crudHistoryService;

    @GetMapping("/all")
    //@Secured({"ROLE_OPERATOR", "ROLE_ADMIN"})
    public String getTransports(Model model) {
        log.debug("GET /all called");

        // add call to history service
        crudHistoryService.add("GET /all", TransportRetrievalDto.class.getName(), "");

        model.addAttribute("transports", transportService.getAll());

        return "management/transports/index";
    }

    @GetMapping("/add")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getTransportCreatePage(Model model) {
        log.debug("GET /add called");

        // add call to history service
        crudHistoryService.add("GET /add", TransportCreationDto.class.getName(), "");

        // creation Dto
        TransportCreationDto transportCreationDto = new TransportCreationDto();

        model.addAttribute("contracts", contractService.getAll());
        model.addAttribute("stations", stationService.getAll());
        model.addAttribute("transportCreationDto", transportCreationDto);

        return "management/transports/add";
    }

    @GetMapping("/update/{uuid}")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String getEmployeeUpdatePage(Model model, @PathVariable UUID uuid) {
        log.debug("GET /update called for UUID {}", uuid);

        // add call to history service
        crudHistoryService.add("GET /update", TransportCreationDto.class.getName(), uuid.toString());

        Optional<TransportRetrievalDto> transportRetrievalDto = transportService.getTransportById(uuid);

        if (transportRetrievalDto.isPresent()) {

            TransportRetrievalDto retrieved = transportRetrievalDto.get();

            TransportCreationDto transportCreationDto = TransportCreationDto.builder()
                    .id(uuid)
                    .contractId(retrieved.getContractId())
                    .stationIds(retrieved.getStationIds())
                    .creationDate(retrieved.getCreationDate())
                    .completionDate(retrieved.getCompletionDate())
                    .companyName(retrieved.getCompanyName())
                    .build();

            model.addAttribute("transportCreationDto", transportCreationDto);
            return "management/transports/update";
        } else {
            return "management/transports/index";
        }
    }

    @PostMapping("/add")
    //@Secured({"ROLE_OPERATOR", "ROLE_ADMIN"})
    public String addTransport(@RequestBody @Validated TransportCreationDto transportDto) {
        log.debug("POST /add called with payload {}", transportDto);

        // add call to history service
        crudHistoryService.add("POST /add", TransportCreationDto.class.getName(), transportDto.toString());

        transportService.addTransport(transportDto);

        return "redirect:/api/transport/all";
    }

    @PutMapping("/update/{uuid}")
    //@Secured({"ROLE_OPERATOR", "ROLE_ADMIN"})
    public String updateTransport(@RequestBody @Validated TransportCreationDto transportDto, @PathVariable UUID uuid) {
        log.debug("PUT /update called with payload {} for UUID {}", transportDto, uuid);

        // add call to history service
        crudHistoryService.add("PUT /update", TransportCreationDto.class.getName(), transportDto.toString());

        transportService.updateTransport(transportDto, uuid);

        return "redirect:/api/transport/all";
    }

    @DeleteMapping("/delete/{uuid}")
    //@Secured({"ROLE_OPERATOR", "ROLE_ADMIN"})
    public String deleteTransport(@PathVariable UUID uuid) {
        log.debug("DELETE /delete called for UUID {}", uuid);

        // add call to history service
        crudHistoryService.add("DELETE /delete", TransportRetrievalDto.class.getName(), uuid.toString());

        Optional<UUID> response = transportService.deleteTransport(uuid);

        log.debug("Database response for DELETE: {}", response);

        return "redirect:/api/transport/all";
    }
}