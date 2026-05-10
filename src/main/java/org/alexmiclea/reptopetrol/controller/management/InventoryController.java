package org.alexmiclea.reptopetrol.controller.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.management.creation.composites.InventoryCreationDto;
import org.alexmiclea.reptopetrol.dto.management.keys.InventoryKeyDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.composites.InventoryRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.keys.InventoryKeyMapper;
import org.alexmiclea.reptopetrol.model.management.keys.InventoryKey;
import org.alexmiclea.reptopetrol.service.management.InventoryService;
import org.alexmiclea.reptopetrol.service.management.ProductService;
import org.alexmiclea.reptopetrol.service.management.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;
    private final StoreService storeService;
    private final ProductService productService;

    @Autowired
    private final InventoryKeyMapper inventoryKeyMapper;

    @GetMapping("/all")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String getInventories(Model model) {
        log.debug("GET /all called");

        model.addAttribute("inventories", inventoryService.getAll());

        return "management/inventories/index";
    }

    @GetMapping("/add")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String getInventoryCreatePage(Model model) {
        log.debug("GET /add called");

        // creation Dto
        InventoryCreationDto inventoryCreationDto = new InventoryCreationDto();

        model.addAttribute("stores", storeService.getAll());
        model.addAttribute("products", productService.getAll());
        model.addAttribute("inventoryCreationDto", inventoryCreationDto);

        return "management/inventories/add";
    }

    @GetMapping("/update")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getFuelSupplyUpdatePage(Model model, InventoryKeyDto inventoryKeyDto) {
        log.debug("GET /update called with ID {}", inventoryKeyDto);

        Optional<InventoryRetrievalDto> inventoryRetrievalDto =
                inventoryService.getInventoryById(inventoryKeyMapper.toInventoryKey(inventoryKeyDto));

        if (inventoryRetrievalDto.isPresent()) {

            InventoryRetrievalDto retrieved = inventoryRetrievalDto.get();

            InventoryCreationDto inventoryCreationDto = InventoryCreationDto.builder()
                    .id(retrieved.getId())
                    .price(retrieved.getPrice())
                    .priceChange(retrieved.getPriceChange())
                    .quantity(retrieved.getQuantity())
                    .build();

            model.addAttribute("inventoryCreationDto", inventoryCreationDto);
            return "management/inventories/update";
        } else {
            return "management/inventories/index";
        }
    }

    @PostMapping("/add")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String addInventory(@RequestBody @Validated InventoryCreationDto inventoryDto) {
        log.debug("POST /add called with payload {}", inventoryDto);
        inventoryService.addInventory(inventoryDto);

        return "redirect:/api/inventory/all";
    }

    @PutMapping("/update")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String updateInventory(@RequestBody @Validated InventoryCreationDto inventoryDto) {
        log.debug("PUT /update called with payload {}", inventoryDto);
        inventoryService.updateInventory(inventoryDto, inventoryDto.getId());

        return "redirect:/api/inventory/all";
    }

    @DeleteMapping("/delete")
    //@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    public String deleteInventory(InventoryKeyDto key) {
        log.debug("DELETE called with composed key {}", key);

        InventoryKey inventoryKey = inventoryKeyMapper.toInventoryKey(key);
        Optional<InventoryKey> inventory = inventoryService.deleteInventory(inventoryKey);

        log.debug("Database response for DELETE: {}", inventory);

        return "redirect:/api/inventory/all";
    }
}
