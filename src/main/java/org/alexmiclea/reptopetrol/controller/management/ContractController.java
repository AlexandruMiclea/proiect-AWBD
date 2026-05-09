package org.alexmiclea.reptopetrol.controller.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.management.creation.ContractCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.ContractRetrievalDto;
import org.alexmiclea.reptopetrol.service.management.ContractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/api/contract")
@RequiredArgsConstructor
@Slf4j
public class ContractController {

    private final ContractService contractService;

    // TODO for all controllers, create GET all, POST and PUT methods with templates for creating, updating and listing
    // all elements. The delete should redirect to the /all endpoint after deletion. Getting one element is something that
    // should be a service-level operation (if you need to show more data, show it )

    @GetMapping("/all")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String getContracts(Model model) {
        log.debug("GET /all called");

        model.addAttribute("contracts", contractService.getAll());

        return "management/contracts/index";
    }

     @PostMapping("/add")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String addContract(@RequestBody @Validated ContractCreationDto contractDto) {
        log.debug("POST /add called with payload {}", contractDto);

        contractService.addContract(contractDto);

        return "management/contracts/add";
    }

    @PutMapping("/update/{uuid}")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String updateContract(@RequestBody @Validated ContractCreationDto contractDto, @PathVariable UUID uuid) {
        log.info("PUT /update called with payload {} for UUID {}", contractDto, uuid);

        contractService.updateContract(contractDto, uuid);

        return "management/contracts/edit";
    }

    @DeleteMapping("/delete/{uuid}")
    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
    public String deleteContract(@PathVariable UUID uuid) {
        log.info("DELETE /delete called for UUID {}", uuid);

        Optional<UUID> response = contractService.deleteContract(uuid);

        log.debug("Database response for DELETE: {}", response);

        return "redirect:api/contract/all";
    }

//    @GetMapping("/{uuid}")
//    //@Secured({"ROLE_OPERATIONAL", "ROLE_ADMIN"})
//    public ResponseEntity<ContractRetrievalDto> getContract(@PathVariable UUID uuid) {
//        log.info("GET /{} called", uuid);
//        Optional<ContractRetrievalDto> contract = contractService.getContractById(uuid);
//        log.debug("Database response for GET: {}", contract);
//
//        return contract.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//    }
}