package org.alexmiclea.reptopetrol.controller.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.management.creation.StationCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.StationRetrievalDto;
import org.alexmiclea.reptopetrol.service.management.StationService;
import org.springframework.http.ResponseEntity;
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

    // TODO OPERATIONAL sees all stations, ADMIN sees all stations, MANAGER sees only the station he has a claim over.
    @GetMapping("/all")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN", "ROLE_MANAGER"})
    public String getStations(Model model) {
        log.debug("GET /all called");

        model.addAttribute("stations", stationService.getAll());

        return "management/stations/index";
    }

    @GetMapping("/add")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getStationCreatePage(Model model) {
        log.debug("GET /add called");

        // creation Dto
        StationCreationDto stationCreationDto = new StationCreationDto();

        // TODO you need to add a list of other elements, so you can have a dropdown and select them

        model.addAttribute("stationCreationDto", stationCreationDto);

        return "management/stations/add";
    }

    @GetMapping("/update/{uuid}")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getContractUpdatePage(Model model, @PathVariable UUID uuid) {
        log.debug("GET /update called for UUID {}", uuid);

        Optional<StationRetrievalDto> stationRetrievalDto = stationService.getStationById(uuid);

        if (stationRetrievalDto.isPresent()) {

            StationRetrievalDto retrieved = stationRetrievalDto.get();

            // TODO you need to get all fuelSupplyKeys by the station id and add them to the update
            // (i.e. not update anything in the station service)

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

        stationService.addStation(stationDto);

        return "redirect:/api/station/all";
    }

    @PutMapping("/update/{uuid}")
    public String updateStation(@RequestBody @Validated StationCreationDto stationDto, @PathVariable UUID uuid) {
        log.debug("PUT /update called with payload {} for UUID {}", stationDto, uuid);

        stationService.updateStation(stationDto, uuid);

        return "redirect:/api/station/all";
    }

    @DeleteMapping("/delete/{uuid}")
    public String deleteStation(@PathVariable UUID uuid) {
        log.debug("DELETE /delete called for UUID {}", uuid);

        Optional<UUID> response = stationService.deleteStation(uuid);

        log.debug("Database response for DELETE: {}", response);

        return "redirect:/api/station/all";
    }
}