package org.alexmiclea.reptopetrol.controller.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.management.creation.ContractCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.ContractRetrievalDto;
import org.alexmiclea.reptopetrol.service.management.ContractService;
import org.alexmiclea.reptopetrol.service.management.FuelService;
import org.alexmiclea.reptopetrol.service.management.SupplierService;
import org.alexmiclea.reptopetrol.service.monitoring.CRUDHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/api/contract")
@RequiredArgsConstructor
@Slf4j
public class ContractController {

    private final ContractService contractService;
    private final SupplierService supplierService;
    private final FuelService fuelService;

    private final CRUDHistoryService crudHistoryService;

    @GetMapping("/all")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getContracts(Model model) {
        log.debug("GET /all called");

        // add call to history service
        crudHistoryService.add("GET /all", ContractRetrievalDto.class.getName(), "");

        model.addAttribute("contracts", contractService.getAll());

        return "management/contracts/index";
    }

    @GetMapping("/add")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getContractCreatePage(Model model) {
        log.debug("GET /add called");

        // add call to history service
        crudHistoryService.add("GET /add", ContractCreationDto.class.getName(), "");

        // creation Dto
        ContractCreationDto contractCreationDto = new ContractCreationDto();

        model.addAttribute("suppliers", supplierService.getAll());
        model.addAttribute("fuels", fuelService.getAll());
        model.addAttribute("contractCreationDto", contractCreationDto);

        return "management/contracts/add";
    }

    @GetMapping("/update/{uuid}")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getContractUpdatePage(Model model, @PathVariable UUID uuid) {
        log.debug("GET /update called for UUID {}", uuid);

        // add call to history service
        crudHistoryService.add("GET /update", ContractCreationDto.class.getName(), uuid.toString());

        Optional<ContractRetrievalDto> contractRetrievalDto = contractService.getContractById(uuid);

        if (contractRetrievalDto.isPresent()) {

            ContractRetrievalDto retrieved = contractRetrievalDto.get();

            ContractCreationDto contractCreationDto = ContractCreationDto.builder()
                    .id(uuid)
                    .supplierId(retrieved.getSupplierId())
                    .fuelIds(retrieved.getFuelIds())
                    .beginDate(retrieved.getBeginDate())
                    .endDate(retrieved.getEndDate())
                    .build();

            model.addAttribute("contractCreationDto", contractCreationDto);
            model.addAttribute("fuels", fuelService.getAll());
            return "management/contracts/update";
        } else {
            return "management/contracts/index";
        }
    }

    @PostMapping("/add")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String addContract(@RequestBody @Validated ContractCreationDto contractDto) {
        log.debug("POST /add called with payload {}", contractDto);

        // add call to history service
        crudHistoryService.add("POST /add", ContractCreationDto.class.getName(), contractDto.toString());

        contractService.addContract(contractDto);

        return "redirect:/api/contract/all";
    }

    @PutMapping("/update/{uuid}")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String updateContract(@RequestBody @Validated ContractCreationDto contractDto, @PathVariable UUID uuid) {
        log.debug("PUT /update called with payload {} for UUID {}", contractDto, uuid);

        // add call to history service
        crudHistoryService.add("PUT /update", ContractCreationDto.class.getName(), contractDto.toString());

        contractService.updateContract(contractDto, uuid);

        return "redirect:/api/contract/all";
    }

    @DeleteMapping("/delete/{uuid}")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String deleteContract(@PathVariable UUID uuid) {
        log.debug("DELETE /delete called for UUID {}", uuid);

        // add call to history service
        crudHistoryService.add("DELETE /delete", ContractRetrievalDto.class.getName(), uuid.toString());

        Optional<UUID> response = contractService.deleteContract(uuid);

        log.debug("Database response for DELETE: {}", response);

        return "redirect:/api/contract/all";
    }
}