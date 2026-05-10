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
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/all")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN", "ROLE_MANAGER"})
    public String getFuelSupplies(Model model) {
        log.debug("GET /all called");

        model.addAttribute("fuelSupplies", fuelSupplyService.getAll());

        return "management/fuelSupplies/index";
    }

    @GetMapping("/add")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getFuelSupplyCreatePage(Model model) {
        log.debug("GET /add called");

        // creation Dto
        FuelSupplyCreationDto fuelSupplyCreationDto = new FuelSupplyCreationDto();

        // TODO you need to add a list of other elements, so you can have a dropdown and select them
        model.addAttribute("stations", stationService.getAll());
        model.addAttribute("fuels", fuelService.getAll());
        model.addAttribute("fuelSupplyCreationDto", fuelSupplyCreationDto);

        return "management/fuelSupplies/add";
    }

    @GetMapping("/update")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getFuelSupplyUpdatePage(Model model, FuelSupplyKeyDto fuelSupplyKeyDto) {
        log.debug("GET /update called with ID {}", fuelSupplyKeyDto);

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
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String addFuelSupply(@RequestBody @Validated FuelSupplyCreationDto fuelSupplyDto) {
        log.debug("POST /add called with payload {}", fuelSupplyDto);

        fuelSupplyService.addFuelSupply(fuelSupplyDto);

        return "redirect:/api/fuelSupply/all";
    }

    @PutMapping("/update")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String updateFuelSupply(@RequestBody @Validated FuelSupplyCreationDto fuelSupplyDto) {
        log.debug("PUT /update called with payload {}", fuelSupplyDto);

        fuelSupplyService.updateFuelSupply(fuelSupplyDto, fuelSupplyDto.getId());

        return "redirect:/api/fuelSupply/all";
    }

    @DeleteMapping("/delete")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String deleteFuelSupply(FuelSupplyKeyDto key) {
        log.debug("DELETE called with composed key {}", key);

        FuelSupplyKey fuelSupplyKey = fuelSupplyKeyMapper.toFuelSupplyKey(key);
        Optional<FuelSupplyKey> fuelSupply = fuelSupplyService.deleteFuelSupply(fuelSupplyKey);

        log.debug("Database response for DELETE: {}", fuelSupply);

        return "redirect:/api/fuelSupply/all";
    }
}
