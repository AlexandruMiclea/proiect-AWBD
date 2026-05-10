package org.alexmiclea.reptopetrol.controller.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.management.creation.FuelCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.FuelRetrievalDto;
import org.alexmiclea.reptopetrol.service.management.FuelService;
import org.alexmiclea.reptopetrol.service.management.StationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/api/fuel")
@RequiredArgsConstructor
@Slf4j
public class FuelController {

    private final FuelService fuelService;
    private final StationService stationService;

    @GetMapping("/all")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN", "ROLE_MANAGER"})
    public String getFuels(Model model) {
        log.debug("GET /all called");

        model.addAttribute("fuels", fuelService.getAll());

        return "management/fuels/index";
    }

    @GetMapping("/add")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getFuelCreatePage(Model model) {
        log.debug("GET /add called");

        // creation Dto
        FuelCreationDto fuelCreationDto = new FuelCreationDto();

        // TODO you need to add a list of other elements, so you can have a dropdown and select them

        model.addAttribute("fuelCreationDto", fuelCreationDto);

        return "management/fuels/add";
    }

    @GetMapping("/update/{uuid}")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getFuelUpdatePage(Model model, @PathVariable UUID uuid) {
        log.debug("GET /update called for UUID {}", uuid);

        Optional<FuelRetrievalDto> fuelRetrievalDto = fuelService.getFuelById(uuid);

        if (fuelRetrievalDto.isPresent()) {

            FuelRetrievalDto retrieved = fuelRetrievalDto.get();

            FuelCreationDto fuelCreationDto = FuelCreationDto.builder()
                    .id(uuid)
                    .name(retrieved.getName())
                    .type(retrieved.getType())
                    .build();

            model.addAttribute("fuelCreationDto", fuelCreationDto);
            return "management/fuels/update";
        } else {
            return "management/fuels/index";
        }
    }

    @PostMapping("/add")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String addFuel(@RequestBody @Validated FuelCreationDto fuelDto) {
        log.debug("POST /add called with payload {}", fuelDto);

        fuelService.addFuel(fuelDto);

        return "redirect:/api/contract/all";
    }

    @PutMapping("/update/{uuid}")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String updateFuel(@RequestBody @Validated FuelCreationDto fuelDto, @PathVariable UUID uuid) {
        log.debug("PUT /update called with payload {} for UUID {}", fuelDto, uuid);

        fuelService.updateFuel(fuelDto, uuid);

        return "redirect:/api/contract/all";
    }

    @DeleteMapping("/delete/{uuid}")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String deleteFuel(@PathVariable UUID uuid) {
        log.debug("DELETE /delete called for UUID {}", uuid);

        Optional<UUID> response = fuelService.deleteFuel(uuid);

        log.debug("Database response for DELETE: {}", response);

        return "redirect:/api/contract/all";
    }
}