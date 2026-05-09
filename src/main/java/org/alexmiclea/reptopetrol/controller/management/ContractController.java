package org.alexmiclea.reptopetrol.controller.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.management.creation.ContractCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.ContractRetrievalDto;
import org.alexmiclea.reptopetrol.service.management.ContractService;
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

    @GetMapping("/all")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getContracts(Model model) {
        log.debug("GET /all called");

        model.addAttribute("contracts", contractService.getAll());

        return "management/contracts/index";
    }

    @GetMapping("/add")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getContractCreatePage(Model model) {
        log.debug("GET /add called");

        // creation Dto
        ContractCreationDto contractCreationDto = new ContractCreationDto();

        // TODO you need to add a list of other elements, so you can have a dropdown and select them

        model.addAttribute("contractCreationDto", contractCreationDto);

        return "management/contracts/add";
    }

    @GetMapping("/update/{uuid}")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getContractUpdatePage(Model model, @PathVariable UUID uuid) {
        log.debug("PUT /update called for UUID {}", uuid);

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
            return "management/contracts/update";
        } else {
            return "management/contracts/index";
        }
    }

    @PostMapping("/add")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String addContract(@RequestBody @Validated ContractCreationDto contractDto) {
        log.debug("POST /add called with payload {}", contractDto);

        contractService.addContract(contractDto);

        return "redirect:api/contract/all";
    }

    @PutMapping("/update/{uuid}")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String updateContract(@RequestBody @Validated ContractCreationDto contractDto, @PathVariable UUID uuid) {
        log.debug("PUT /update called with payload {} for UUID {}", contractDto, uuid);

        contractService.updateContract(contractDto, uuid);

        return "redirect:api/contract/all";
    }

    @DeleteMapping("/delete/{uuid}")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String deleteContract(@PathVariable UUID uuid) {
        log.debug("DELETE /delete called for UUID {}", uuid);

        Optional<UUID> response = contractService.deleteContract(uuid);

        log.debug("Database response for DELETE: {}", response);

        return "redirect:api/contract/all";
    }
}