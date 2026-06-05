package org.alexmiclea.reptopetrol.controller.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.management.creation.composites.FuelSupplyCreationDto;
import org.alexmiclea.reptopetrol.dto.management.keys.FuelSupplyKeyDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.composites.FuelSupplyRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.keys.FuelSupplyKeyMapper;
import org.alexmiclea.reptopetrol.model.management.keys.FuelSupplyKey;
import org.alexmiclea.reptopetrol.service.management.FuelService;
import org.alexmiclea.reptopetrol.service.management.FuelSupplyService;
import org.alexmiclea.reptopetrol.service.management.StationService;
import org.alexmiclea.reptopetrol.service.monitoring.CRUDHistoryService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/fuelSupply")
@RequiredArgsConstructor
@Slf4j
public class FuelSupplyController {

    private final FuelSupplyService fuelSupplyService;
    private final StationService stationService;
    private final FuelService fuelService;

    private final FuelSupplyKeyMapper fuelSupplyKeyMapper;

    private final CRUDHistoryService crudHistoryService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_OPERATIONAL') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public String getFuelSupplies(Model model) {
        log.debug("GET /all called");

        // add call to history service
        crudHistoryService.add("GET /all", FuelSupplyRetrievalDto.class.getName(), "");

        model.addAttribute("fuelSupplies", fuelSupplyService.getAll());

        return "management/fuelSupplies/index";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_OPERATIONAL') or hasRole('ROLE_ADMIN')")
    public String getFuelSupplyCreatePage(Model model) {
        log.debug("GET /add called");

        // add call to history service
        crudHistoryService.add("GET /add", FuelSupplyCreationDto.class.getName(), "");

        // creation Dto
        FuelSupplyCreationDto fuelSupplyCreationDto = new FuelSupplyCreationDto();

        model.addAttribute("stations", stationService.getAll());
        model.addAttribute("fuels", fuelService.getAll());
        model.addAttribute("fuelSupplyCreationDto", fuelSupplyCreationDto);

        return "management/fuelSupplies/add";
    }

    @GetMapping("/update")
    @PreAuthorize("hasRole('ROLE_OPERATIONAL') or hasRole('ROLE_ADMIN')")
    public String getFuelSupplyUpdatePage(Model model, FuelSupplyKeyDto fuelSupplyKeyDto) {
        log.debug("GET /update called with ID {}", fuelSupplyKeyDto);

        // add call to history service
        crudHistoryService.add("GET /update", FuelSupplyCreationDto.class.getName(), fuelSupplyKeyDto.toString());

        Optional<FuelSupplyRetrievalDto> fuelSupplyRetrievalDto =
                fuelSupplyService.getFuelSupplyById(fuelSupplyKeyMapper.toFuelSupplyKey(fuelSupplyKeyDto));

        if (fuelSupplyRetrievalDto.isPresent()) {

            FuelSupplyRetrievalDto retrieved = fuelSupplyRetrievalDto.get();

            FuelSupplyCreationDto fuelSupplyCreationDto = FuelSupplyCreationDto.builder()
                    .id(retrieved.getId())
                    .price(retrieved.getPrice())
                    .priceChange(retrieved.getPriceChange())
                    .quantity(retrieved.getQuantity())
                    .build();

            model.addAttribute("fuelSupplyCreationDto", fuelSupplyCreationDto);
            return "management/fuelSupplies/update";
        } else {
            return "management/fuelSupplies/index";
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_OPERATIONAL') or hasRole('ROLE_ADMIN')")
    public String addFuelSupply(@RequestBody @Validated FuelSupplyCreationDto fuelSupplyDto) {
        log.debug("POST /add called with payload {}", fuelSupplyDto);

        // add call to history service
        crudHistoryService.add("POST /add", FuelSupplyCreationDto.class.getName(), fuelSupplyDto.toString());

        fuelSupplyService.addFuelSupply(fuelSupplyDto);

        return "redirect:/api/fuelSupply/all";
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_OPERATIONAL') or hasRole('ROLE_ADMIN')")
    public String updateFuelSupply(@RequestBody @Validated FuelSupplyCreationDto fuelSupplyDto) {
        log.debug("PUT /update called with payload {}", fuelSupplyDto);

        // add call to history service
        crudHistoryService.add("PUT /update", FuelSupplyCreationDto.class.getName(), fuelSupplyDto.toString());

        fuelSupplyService.updateFuelSupply(fuelSupplyDto, fuelSupplyDto.getId());

        return "redirect:/api/fuelSupply/all";
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_OPERATIONAL') or hasRole('ROLE_ADMIN')")
    public String deleteFuelSupply(FuelSupplyKeyDto key) {
        log.debug("DELETE called with composed key {}", key);

        // add call to history service
        crudHistoryService.add("DELETE /delete", FuelSupplyRetrievalDto.class.getName(), key.toString());

        FuelSupplyKey fuelSupplyKey = fuelSupplyKeyMapper.toFuelSupplyKey(key);
        Optional<FuelSupplyKey> fuelSupply = fuelSupplyService.deleteFuelSupply(fuelSupplyKey);

        log.debug("Database response for DELETE: {}", fuelSupply);

        return "redirect:/api/fuelSupply/all";
    }
}
