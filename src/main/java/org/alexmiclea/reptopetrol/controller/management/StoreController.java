package org.alexmiclea.reptopetrol.controller.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.management.creation.StoreCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.StoreRetrievalDto;
import org.alexmiclea.reptopetrol.service.management.StationService;
import org.alexmiclea.reptopetrol.service.management.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

// TODO you need to add the station service and update the station to link the store id
// for the POST and PUT instructions

@Controller
@RequestMapping("/api/store")
@RequiredArgsConstructor
@Slf4j
public class StoreController {

    private final StoreService storeService;

    private final StationService stationService;

    @GetMapping("/all")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String getStores(Model model) {
        log.debug("GET /all called");

        model.addAttribute("stores", storeService.getAll());

        return "management/stores/index";
    }

    @GetMapping("/add")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getStoreCreatePage(Model model) {
        log.debug("GET /add called");

        // creation Dto
        StoreCreationDto storeCreationDto = new StoreCreationDto();

        model.addAttribute("stations", stationService.getAll());
        model.addAttribute("storeCreationDto", storeCreationDto);

        return "management/stores/add";
    }

    @GetMapping("/update/{uuid}")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String getStoreUpdatePage(Model model, @PathVariable UUID uuid) {
        log.debug("GET /update called for UUID {}", uuid);

        Optional<StoreRetrievalDto> storeRetrievalDto = storeService.getStoreById(uuid);

        if (storeRetrievalDto.isPresent()) {

            StoreRetrievalDto retrieved = storeRetrievalDto.get();

            StoreCreationDto storeCreationDto = StoreCreationDto.builder()
                    .id(uuid)
                    .build();

            model.addAttribute("storeCreationDto", storeCreationDto);
            return "management/stores/update";
        } else {
            return "management/stores/index";
        }
    }

    @PostMapping("/add")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String addStore(@RequestBody @Validated StoreCreationDto storeDto) {
        log.debug("POST /add called with payload {}", storeDto);

        storeService.addStore(storeDto);

        return "redirect:/api/store/all";
    }

    @PutMapping("/update/{uuid}")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String updateStore(@RequestBody @Validated StoreCreationDto storeDto, @PathVariable UUID uuid) {
        log.debug("PUT /update called with payload {} for UUID {}", storeDto, uuid);

        storeService.updateStore(storeDto, uuid);

        return "redirect:/api/store/all";
    }

    @DeleteMapping("/delete/{uuid}")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String deleteStore(@PathVariable UUID uuid) {
        log.debug("DELETE /delete called for UUID {}", uuid);

        Optional<UUID> response = storeService.deleteStore(uuid);

        log.debug("Database response for DELETE: {}", response);

        return "redirect:/api/store/all";
    }
}