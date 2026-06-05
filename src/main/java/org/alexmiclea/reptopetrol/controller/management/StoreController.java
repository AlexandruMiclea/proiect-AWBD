package org.alexmiclea.reptopetrol.controller.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.management.creation.StoreCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.StoreRetrievalDto;
import org.alexmiclea.reptopetrol.service.management.StationService;
import org.alexmiclea.reptopetrol.service.management.StoreService;
import org.alexmiclea.reptopetrol.service.monitoring.CRUDHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/api/store")
@RequiredArgsConstructor
@Slf4j
public class StoreController {

    private final StoreService storeService;

    private final StationService stationService;

    private final CRUDHistoryService crudHistoryService;

    @GetMapping("/all")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String getStores(Model model) {
        log.debug("GET /all called");

        // add call to history service
        crudHistoryService.add("GET /all", StoreRetrievalDto.class.getName(), "");

        model.addAttribute("stores", storeService.getAll());

        return "management/stores/index";
    }

    @GetMapping("/add")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getStoreCreatePage(Model model) {
        log.debug("GET /add called");

        // add call to history service
        crudHistoryService.add("GET /add", StoreCreationDto.class.getName(), "");

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

        // add call to history service
        crudHistoryService.add("GET /update", StoreCreationDto.class.getName(), uuid.toString());

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

        // add call to history service
        crudHistoryService.add("POST /add", StoreCreationDto.class.getName(), storeDto.toString());

        storeService.addStore(storeDto);

        return "redirect:/api/store/all";
    }

    @PutMapping("/update/{uuid}")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String updateStore(@RequestBody @Validated StoreCreationDto storeDto, @PathVariable UUID uuid) {
        log.debug("PUT /update called with payload {} for UUID {}", storeDto, uuid);

        // add call to history service
        crudHistoryService.add("PUT /update", StoreCreationDto.class.getName(), storeDto.toString());

        storeService.updateStore(storeDto, uuid);

        return "redirect:/api/store/all";
    }

    @DeleteMapping("/delete/{uuid}")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String deleteStore(@PathVariable UUID uuid) {
        log.debug("DELETE /delete called for UUID {}", uuid);

        // add call to history service
        crudHistoryService.add("DELETE /delete", StoreRetrievalDto.class.getName(), uuid.toString());

        Optional<UUID> response = storeService.deleteStore(uuid);

        log.debug("Database response for DELETE: {}", response);

        return "redirect:/api/store/all";
    }
}