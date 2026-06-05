package org.alexmiclea.reptopetrol.controller.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.management.creation.StationCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.StationRetrievalDto;
import org.alexmiclea.reptopetrol.service.management.StationService;
import org.alexmiclea.reptopetrol.service.monitoring.CRUDHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/api/station")
@RequiredArgsConstructor
@Slf4j
public class StationController {

    private final StationService stationService;

    private final CRUDHistoryService crudHistoryService;

    // TODO OPERATIONAL sees all stations, ADMIN sees all stations, MANAGER sees only the station he has a claim over.
    @GetMapping("/all")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN", "ROLE_MANAGER"})
    public String getStations(Model model) {
        log.debug("GET /all called");

        // add call to history service
        crudHistoryService.add("GET /all", StationRetrievalDto.class.getName(), "");

        model.addAttribute("stations", stationService.getAll());

        return "management/stations/index";
    }

    @GetMapping("/add")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getStationCreatePage(Model model) {
        log.debug("GET /add called");

        // add call to history service
        crudHistoryService.add("GET /add", StationCreationDto.class.getName(), "");

        // creation Dto
        StationCreationDto stationCreationDto = new StationCreationDto();

        model.addAttribute("stationCreationDto", stationCreationDto);

        return "management/stations/add";
    }

    @GetMapping("/update/{uuid}")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getContractUpdatePage(Model model, @PathVariable UUID uuid) {
        log.debug("GET /update called for UUID {}", uuid);

        // add call to history service
        crudHistoryService.add("GET /update", StationCreationDto.class.getName(), uuid.toString());

        Optional<StationRetrievalDto> stationRetrievalDto = stationService.getStationById(uuid);

        if (stationRetrievalDto.isPresent()) {

            StationRetrievalDto retrieved = stationRetrievalDto.get();

            StationCreationDto stationCreationDto = StationCreationDto.builder()
                    .id(uuid)
                    .name(retrieved.getName())
                    .address(retrieved.getAddress())
                    .pumpNo(retrieved.getPumpNo())
                    .build();

            model.addAttribute("stationCreationDto", stationCreationDto);
            return "management/stations/update";
        } else {
            return "management/stations/index";
        }
    }

    @PostMapping("/add")
    public String addStation(@RequestBody @Validated StationCreationDto stationDto) {
        log.debug("POST /add called with payload {}", stationDto);

        // add call to history service
        crudHistoryService.add("POST /add", StationCreationDto.class.getName(), stationDto.toString());

        stationService.addStation(stationDto);

        return "redirect:/api/station/all";
    }

    @PutMapping("/update/{uuid}")
    public String updateStation(@RequestBody @Validated StationCreationDto stationDto, @PathVariable UUID uuid) {
        log.debug("PUT /update called with payload {} for UUID {}", stationDto, uuid);

        // add call to history service
        crudHistoryService.add("PUT /update", StationCreationDto.class.getName(), stationDto.toString());

        stationService.updateStation(stationDto, uuid);

        return "redirect:/api/station/all";
    }

    @DeleteMapping("/delete/{uuid}")
    public String deleteStation(@PathVariable UUID uuid) {
        log.debug("DELETE /delete called for UUID {}", uuid);

        // add call to history service
        crudHistoryService.add("DELETE /delete", StationRetrievalDto.class.getName(), uuid.toString());

        Optional<UUID> response = stationService.deleteStation(uuid);

        log.debug("Database response for DELETE: {}", response);

        return "redirect:/api/station/all";
    }
}