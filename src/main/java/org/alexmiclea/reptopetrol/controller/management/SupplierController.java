package org.alexmiclea.reptopetrol.controller.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.management.creation.SupplierCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.SupplierRetrievalDto;
import org.alexmiclea.reptopetrol.service.management.SupplierService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/api/supplier")
@RequiredArgsConstructor
@Slf4j
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping("/all")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getSuppliers(Model model) {
        log.debug("GET /all called");

        model.addAttribute("suppliers", supplierService.getAll());

        return "management/suppliers/index";
    }

    @GetMapping("/add")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getSupplierCreatePage(Model model) {
        log.debug("GET /add called");

        // creation Dto
        SupplierCreationDto supplierCreationDto = new SupplierCreationDto();

        model.addAttribute("supplierCreationDto", supplierCreationDto);

        return "management/suppliers/add";
    }

    @GetMapping("/update/{uuid}")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getSupplierUpdatePage(Model model, @PathVariable UUID uuid) {
        log.debug("GET /update called for UUID {}", uuid);

        Optional<SupplierRetrievalDto> supplierRetrievalDto = supplierService.getSupplierById(uuid);

        if (supplierRetrievalDto.isPresent()) {

            SupplierRetrievalDto retrieved = supplierRetrievalDto.get();

            SupplierCreationDto supplierCreationDto = SupplierCreationDto.builder()
                    .id(uuid)
                    .name(retrieved.getName())
                    .address(retrieved.getAddress())
                    .homeCountry(retrieved.getHomeCountry())
                    .build();

            model.addAttribute("supplierCreationDto", supplierCreationDto);
            return "management/suppliers/update";
        } else {
            return "management/suppliers/index";
        }
    }

    @PostMapping("/add")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String addSupplier(@RequestBody @Validated SupplierCreationDto supplierDto) {
        log.debug("POST /add called with payload {}", supplierDto);

        supplierService.addSupplier(supplierDto);

        return "redirect:/api/supplier/all";
    }

    @PutMapping("/update/{uuid}")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String updateSupplier(@RequestBody @Validated SupplierCreationDto supplierDto, @PathVariable UUID uuid) {
        log.debug("PUT /update called with payload {} for UUID {}", supplierDto, uuid);

        supplierService.updateSupplier(supplierDto, uuid);

        return "redirect:/api/supplier/all";
    }

    @DeleteMapping("/delete/{uuid}")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String deleteSupplier(@PathVariable UUID uuid) {
        log.debug("DELETE /update called for UUID {}", uuid);

        Optional<UUID> response = supplierService.deleteSupplier(uuid);

        log.debug("Database response for DELETE: {}", response);

        return "redirect:/api/supplier/all";
    }
}
